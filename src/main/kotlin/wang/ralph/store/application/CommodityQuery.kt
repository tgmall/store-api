package wang.ralph.store.application

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.dtos.commodity.CommodityDto
import wang.ralph.store.application.dtos.commodity.toDto
import wang.ralph.store.models.commodity.Commodity
import java.util.*

class CommodityQuery {
    fun commodities(tags: List<String>): List<CommodityDto> = transaction {
        Commodity.findByTags(tags).map { it.toDto() }
    }

    fun commodity(id: String): CommodityDto = transaction {
        Commodity[UUID.fromString(id)].toDto()
    }
}
