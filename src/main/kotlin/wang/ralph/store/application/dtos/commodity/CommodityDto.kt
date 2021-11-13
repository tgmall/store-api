package wang.ralph.store.application.dtos.commodity

import wang.ralph.store.models.commodity.Commodity

fun Commodity.toDto(): CommodityDto = CommodityDto(id.toString(), name, skus.map { it.toDto() })
data class CommodityDto(val id: String, val name: String, val skus: List<SkuDto>)
