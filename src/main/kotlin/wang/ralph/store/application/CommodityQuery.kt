package wang.ralph.store.application

import wang.ralph.store.application.dtos.commodity.CommodityDto
import wang.ralph.store.application.dtos.commodity.toDto
import wang.ralph.store.models.commodity.Commodity

class CommodityQuery {
    fun query(tags: List<String>): List<CommodityDto> {
        return Commodity.findByTags(*tags.toTypedArray()).map { it.toDto() }
    }
}
