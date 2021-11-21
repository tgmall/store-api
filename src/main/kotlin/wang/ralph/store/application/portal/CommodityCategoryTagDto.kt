package wang.ralph.store.application.portal

import wang.ralph.store.models.portal.CommodityCategoryTag

fun CommodityCategoryTag.toDto(): CommodityCategoryTagDto = CommodityCategoryTagDto(id.toString(), tag)
data class CommodityCategoryTagDto(val id: String, val tag: String)
