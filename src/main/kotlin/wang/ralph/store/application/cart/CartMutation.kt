package wang.ralph.store.application.cart

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.application.purchase.PurchaseOrderDto
import wang.ralph.store.application.purchase.toDto
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.models.commodity.Sku
import wang.ralph.store.models.commodity.Skus
import wang.ralph.store.models.purchase.PurchaseOrder
import wang.ralph.store.plugins.subject
import java.math.BigDecimal
import java.util.*

@GraphQLDescription("修改购物车")
class CartMutation {
    @GraphQLDescription("添加购物车条目")
    fun addCartItem(dfe: DataFetchingEnvironment, skuId: String, skuAmount: BigDecimal): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().userId)
        cart.addItem(UUID.fromString(skuId), skuAmount)
        cart.toDto()
    }

    @GraphQLDescription("减少购物车条目")
    fun removeCartItem(dfe: DataFetchingEnvironment, skuId: String, skuAmount: BigDecimal): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().userId)
        cart.removeItem(UUID.fromString(skuId), skuAmount)
        cart.toDto()
    }

    @GraphQLDescription("清理购物车，移除已经减为零的")
    fun purgeCart(dfe: DataFetchingEnvironment): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().userId)
        cart.purge()
        cart.toDto()
    }

    fun createPurchaseOrder(
        dfe: DataFetchingEnvironment,
        cartItemIds: List<String>,
        receiverName: String?,
        receiverMobile: String,
        address: String,
        postcode: String,
    ): PurchaseOrderDto = transaction {
        val subject = dfe.call.subject()
        val cart = Cart.ensureCart(subject.userId)
        val purchaseOrder = PurchaseOrder.create(
            userId = subject.userId,
            receiverName = receiverName,
            receiverMobile = receiverMobile,
            address = address,
            postcode = postcode,
        )
        val items = cart.itemsById(cartItemIds.map { UUID.fromString(it) })
        items.forEach {
            val sku = Sku.find { Skus.id eq it.skuId }.first()
            purchaseOrder.addItem(sku, it.skuAmount)
            it.delete()
        }
        purchaseOrder.toDto()
    }
}
