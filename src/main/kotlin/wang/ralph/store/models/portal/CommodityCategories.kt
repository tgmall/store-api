package wang.ralph.store.models.portal

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CommodityCategories : UUIDTable("commodity_category") {
    val name = varchar("name", 32)
    val parent = reference("parent_id", CommodityCategories).nullable()
}

class CommodityCategory(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityCategory>(CommodityCategories)

    var name: String by CommodityCategories.name
    var parent by CommodityCategory optionalReferencedOn CommodityCategories.parent
    val children by CommodityCategory optionalReferrersOn CommodityCategories.parent
    val tags by CommodityCategoryTag referrersOn CommodityCategoryTags.category
}
