package wang.ralph.store.application.dtos.product

import wang.ralph.store.models.product.SkuImage

fun SkuImage.toDto(): SkuImageDto = SkuImageDto(imageUri)
data class SkuImageDto(val imageUri: String)
