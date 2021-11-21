package wang.ralph.store.application.commodity

import wang.ralph.store.models.commodity.Sku
import java.math.BigDecimal

fun Sku.toDto(): SkuDto =
    SkuDto(id.toString(), name, description, images.map { it.toDto() }, unit, price)

data class SkuDto(
    val id: String,
    val name: String,
    val description: String,
    val images: List<SkuImageDto>,
    val unit: String,
    val price: BigDecimal?,
)
