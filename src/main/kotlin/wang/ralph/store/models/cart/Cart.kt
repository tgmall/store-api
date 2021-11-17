package wang.ralph.store.models.cart

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object Carts : UUIDTable("cart") {
    val userId = uuid("user_id")
}

@GraphQLDescription("购物车")
class Cart(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Cart>(Carts) {
        @GraphQLDescription("新建或取得现有购物车")
        fun ensureCart(userId: UUID) = Cart.find { Carts.userId eq userId }.firstOrNull() ?: Cart.new {
            this.userId = userId
        }
    }

    @GraphQLDescription("所属用户的 ID")
    var userId: UUID by Carts.userId

    @GraphQLDescription("购物车中的条目")
    val items by CartItem referrersOn CartItems.cart

    @GraphQLDescription("添加购物车条目")
    fun addItem(skuId: UUID, skuAmount: BigDecimal) {
        val item = items.firstOrNull { it.skuId == skuId }
        if (item == null) {
            CartItem.create(this, skuId, maxOf(skuAmount, BigDecimal.ZERO))
        } else {
            val realAmount = maxOf(item.skuAmount + skuAmount, BigDecimal.ZERO)
            item.skuAmount = realAmount
            item.flush()
        }
    }

    @GraphQLDescription("移除购物车条目（数量可能减为零，但不会删除条目本身）")
    fun removeItem(skuId: UUID, skuAmount: BigDecimal) {
        addItem(skuId, -skuAmount)
    }

    @GraphQLDescription("清理购物车（移除数量为 0 的条目）")
    fun purge() {
        items.filter { it.skuAmount <= BigDecimal.ZERO }.forEach { it.delete() }
    }
}
