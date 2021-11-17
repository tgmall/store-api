package wang.ralph.store.models.cart

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object CartItems : UUIDTable("cart_item") {
    val cart = reference("cart_id", Carts)
    val skuId = uuid("sku_id")
    val skuAmount = decimal("sku_amount", 10, 2).default(BigDecimal.ZERO)
}

@GraphQLDescription("购物车条目")
class CartItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CartItem>(CartItems) {
        @GraphQLDescription("创建条目")
        fun create(cart: Cart, skuId: UUID, skuAmount: BigDecimal): CartItem {
            return CartItem.new {
                this.cart = cart
                this.skuId = skuId
                this.skuAmount = skuAmount
            }
        }
    }

    @GraphQLDescription("所属购物车")
    var cart by Cart referencedOn CartItems.cart

    @GraphQLDescription("SKU 的 ID")
    var skuId: UUID by CartItems.skuId

    @GraphQLDescription("SKU 的数量")
    var skuAmount: BigDecimal by CartItems.skuAmount
}
