package wang.ralph.store.models.cart

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

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

    fun addItem(skuId: UUID, amount: BigDecimal) {
        val item = items.firstOrNull { it.skuId == skuId }
        if (item == null) {
            CartItem.create(this, skuId, maxOf(amount, BigDecimal.ZERO))
        } else {
            val realAmount = maxOf(item.amount + amount, BigDecimal.ZERO)
            item.amount = realAmount
            item.flush()
        }
    }

    fun removeItem(skuId: UUID, amount: BigDecimal) {
        addItem(skuId, -amount)
    }

    fun purge() {
        items.filter { it.amount <= BigDecimal.ZERO }.forEach { it.delete() }
    }
}
