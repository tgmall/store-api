package wang.ralph.store.models.cart

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object CartItems : UUIDTable("cart_item") {
    val cart = reference("cart_id", Carts)
    val skuId = uuid("sku_id")
    val amount = decimal("amount", 10, 2).default(BigDecimal.ZERO)
}

class CartItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CartItem>(CartItems) {
        fun create(cart: Cart, skuId: UUID, amount: BigDecimal): CartItem {
            return CartItem.new {
                this.cart = cart
                this.skuId = skuId
                this.amount = amount
            }
        }
    }

    var cart by Cart referencedOn CartItems.cart
    var skuId: UUID by CartItems.skuId
    var amount: BigDecimal by CartItems.amount

}
