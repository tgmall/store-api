package wang.ralph.store.application.dtos.product

import wang.ralph.store.models.product.Sku

fun Sku.toDto(): SkuDto =
    SkuDto(id.toString(), name, description, images.map { it.toDto() })

data class SkuDto(
    val id: String,
    val name: String,
    val description: String,
    val images: List<SkuImageDto>,
)
