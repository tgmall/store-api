package wang.ralph.store.application.dtos.cart

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.dtos.commodity.SkuDto
import wang.ralph.store.application.dtos.commodity.toDto
import wang.ralph.store.application.exceptions.CommodityNotPricedException
import wang.ralph.store.models.cart.CartItem
import wang.ralph.store.models.commodity.Sku
import java.math.BigDecimal
import java.util.*

fun CartItem.toDto(): CartItemDto = CartItemDto(skuId, amount)
data class CartItemDto(val skuId: UUID, val amount: BigDecimal) {
    fun sku(): SkuDto = transaction { Sku.get(skuId).toDto() }
    fun total(): BigDecimal = (sku().price ?: throw CommodityNotPricedException(skuId.toString())) * amount
}
