package wang.ralph.store.application.commodity

import wang.ralph.store.models.commodity.SkuImage

fun SkuImage.toDto(): SkuImageDto = SkuImageDto(imageUri)
data class SkuImageDto(val imageUri: String)
