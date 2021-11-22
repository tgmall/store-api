package wang.ralph.store.application.shipping

import wang.ralph.store.models.shipping.ShippingOrderItem
import java.math.BigDecimal

fun ShippingOrderItem.toDto(): ShippingOrderItemDto = ShippingOrderItemDto(id.toString(), skuId.toString(), skuAmount)
data class ShippingOrderItemDto(val id: String, val skuId: String, val skuAmount: BigDecimal)
