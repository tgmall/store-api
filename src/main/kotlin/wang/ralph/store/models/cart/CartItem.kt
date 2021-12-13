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
    val skuAmount = decimal("sku_amount", 10, 2).default(BigDecimal.ZERO)
}

// 购物车条目
class CartItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CartItem>(CartItems) {
        // 创建条目
        fun create(cart: Cart, skuId: UUID, skuAmount: BigDecimal): CartItem {
            return CartItem.new {
                this.cart = cart
                this.skuId = skuId
                this.skuAmount = skuAmount
            }
        }
    }

    // 所属购物车
    var cart by Cart referencedOn CartItems.cart

    // SKU 的 ID
    var skuId by CartItems.skuId

    // SKU 的数量
    var skuAmount by CartItems.skuAmount
}
