package wang.ralph.store.application

import wang.ralph.store.application.dtos.portal.CommodityCategoryDto
import wang.ralph.store.application.dtos.portal.toDto
import wang.ralph.store.models.portal.CommodityCategory

class CommodityCategoryQuery {
    fun query(): List<CommodityCategoryDto> {
        return CommodityCategory.tree().map { it.toDto() }
    }
}
