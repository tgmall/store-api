package wang.ralph.store.models.commodity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import java.util.*

object Commodities : UUIDTable("commodity") {
    val name = varchar("name", 255)
    val description = text("description")
}

class Commodity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Commodity>(Commodities) {
        fun findByTags(vararg tags: String): SizedIterable<Commodity> {
            val tagList = tags.distinct().toList()
            return find {
                exists(
                    CommodityTags.select {
                        (CommodityTags.commodity eq Commodities.id) and (CommodityTags.tag inList tagList)
                    }
                )
            }
        }
    }

    var name: String by Commodities.name
    var description: String by Commodities.description
    val skus by Sku referrersOn Skus.commodity
    val tags by CommodityTag referrersOn CommodityTags.commodity
    fun addTags(vararg tags: String): Commodity {
        val commodity = this
        tags.forEach { tag ->
            CommodityTag.new {
                this.commodity = commodity
                this.tag = tag
            }
        }
        return commodity
    }

    fun removeTags(vararg tags: String): Commodity {
        CommodityTags.deleteWhere {
            CommodityTags.tag inList tags.toList()
        }
        return this
    }

}
