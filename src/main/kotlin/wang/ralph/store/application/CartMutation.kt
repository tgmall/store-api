package wang.ralph.store.application

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.application.dtos.cart.CartDto
import wang.ralph.store.application.dtos.cart.toDto
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.plugins.subject
import java.util.*

@GraphQLDescription("修改购物车")
class CartMutation {
    @GraphQLDescription("添加购物车条目")
    fun addCartItem(dfe: DataFetchingEnvironment, skuId: UUID, count: Int): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().subjectId)
        cart.addItem(skuId, count)
        cart.toDto()
    }

    @GraphQLDescription("减少购物车条目")
    fun removeCartItem(dfe: DataFetchingEnvironment, skuId: UUID, count: Int): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().subjectId)
        cart.removeItem(skuId, count)
        cart.toDto()
    }

    @GraphQLDescription("清理购物车，移除已经减为零的")
    fun purgeCart(dfe: DataFetchingEnvironment): CartDto = transaction {
        val cart = Cart.ensureCart(dfe.call.subject().subjectId)
        cart.purge()
        cart.toDto()
    }
}
