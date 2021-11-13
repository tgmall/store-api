package wang.ralph.store.application.dtos.commodity

import wang.ralph.store.models.commodity.Sku

fun Sku.toDto(): SkuDto =
    SkuDto(id.toString(), name, description, images.map { it.toDto() })

data class SkuDto(
    val id: String,
    val name: String,
    val description: String,
    val images: List<SkuImageDto>,
)
