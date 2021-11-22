package wang.ralph.store.application.shipping

import wang.ralph.store.models.shipping.Shipper
import java.math.BigDecimal

fun Shipper.toDto(): ShipperDto = ShipperDto(id.toString(), name, firstWeightFreight, additionalWeightFreight)

data class ShipperDto(
    val id: String,
    val name: String,
    val firstWeightFreight: BigDecimal,
    val additionalWeightFreight: BigDecimal,
)
