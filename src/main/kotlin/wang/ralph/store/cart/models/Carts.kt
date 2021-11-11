package wang.ralph.store.cart.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import kotlin.math.max

object Carts : UUIDTable("cart") {
    val subjectId = uuid("subject_id")
}

class Cart(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Cart>(Carts) {
        fun ensureCart(ownerId: UUID) = Cart.find { Carts.subjectId eq ownerId }.firstOrNull() ?: Cart.new {
            subjectId = ownerId
        }
    }

    var subjectId: UUID by Carts.subjectId
    val items by CartItem referrersOn CartItems.cart

    fun toDto(): CartDto = CartDto(id.toString(), items.map { it.toDto() })
    fun addItem(skuId: UUID, amount: Int) {
        val item = items.firstOrNull { it.skuId == skuId }
        val realAmount = max(0, (item?.amount ?: 0) + amount)
        if (item == null) {
            CartItem.create(this, skuId, realAmount)
        } else {
            item.amount = realAmount
            item.flush()
        }
    }

    fun removeItem(skuId: UUID, amount: Int) {
        addItem(skuId, -amount)
    }

    fun purge() {
        items.filter { it.amount <= 0 }.forEach { it.delete() }
    }
}

data class CartDto(val id: String, val items: List<CartItemDto>)
