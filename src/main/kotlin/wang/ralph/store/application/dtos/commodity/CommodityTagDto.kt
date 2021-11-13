package wang.ralph.store.application.dtos.commodity

import wang.ralph.store.models.commodity.CommodityTag

fun CommodityTag.toDto() = CommodityTagDto(id.toString(), tag)
data class CommodityTagDto(val id: String, val tag: String)
