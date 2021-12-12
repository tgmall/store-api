package wang.ralph.store.application.commodity

import wang.ralph.store.models.commodity.SkuImage

fun SkuImage.toDto(): SkuImageDto = SkuImageDto(largeImageUrl = largeImageUrl, smallImageUrl = smallImageUrl)
data class SkuImageDto(val largeImageUrl: String, val smallImageUrl: String)
