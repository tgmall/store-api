package wang.ralph.store.application.dtos.price

import wang.ralph.store.models.price.Price
import java.math.BigDecimal
import java.time.Instant

fun Price.toDto(): PriceDto = PriceDto(id.toString(), skuId.toString(), amount, validFrom, validTo)
data class PriceDto(
    val id: String,
    val skuId: String,
    val amount: BigDecimal,
    val validFrom: Instant,
    val validTo: Instant,
)
