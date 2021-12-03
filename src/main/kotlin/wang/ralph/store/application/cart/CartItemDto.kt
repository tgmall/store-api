package wang.ralph.store.application.cart

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.commodity.SkuDto
import wang.ralph.store.application.commodity.toDto
import wang.ralph.store.models.cart.CartItem
import wang.ralph.store.models.commodity.CommodityNotPricedException
import wang.ralph.store.models.commodity.Sku
import java.math.BigDecimal
import java.util.*

fun CartItem.toDto(): CartItemDto = CartItemDto(id.toString(), skuId.toString(), skuAmount)
data class CartItemDto(val id: String?, val skuId: String, val skuAmount: BigDecimal) {
    fun sku(): SkuDto = transaction { Sku.get(UUID.fromString(skuId)).toDto() }
    fun total(): BigDecimal = (sku().price ?: throw CommodityNotPricedException(skuId)) * skuAmount
}
