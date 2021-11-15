package wang.ralph.store.application

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.dtos.portal.CommodityCategoryDto
import wang.ralph.store.application.dtos.portal.toDto
import wang.ralph.store.models.portal.CommodityCategory

class CommodityCategoryQuery {
    fun commodityCategories(): List<CommodityCategoryDto> = transaction {
        CommodityCategory.tree().map { it.toDto() }
    }
}
