package wang.ralph.store.models.cart

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CartItems : UUIDTable("cart_item") {
    val cart = reference("cart_id", Carts)
    val skuId = uuid("sku_id")
    val amount = integer("amount")
}

class CartItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CartItem>(CartItems) {
        fun create(cart: Cart, skuId: UUID, amount: Int): CartItem {
            return CartItem.new {
                this.cart = cart
                this.skuId = skuId
                this.amount = amount
            }
        }
    }

    var cart by Cart referencedOn CartItems.cart
    var skuId: UUID by CartItems.skuId
    var amount: Int by CartItems.amount

}
