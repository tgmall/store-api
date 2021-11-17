package wang.ralph.store.models.portal

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CommodityCategoryTags : UUIDTable("commodity_category_tag") {
    val category = reference("group_id", CommodityCategories)
    val tag = varchar("tag", 32)
}

class CommodityCategoryTag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityCategoryTag>(CommodityCategoryTags)

    var category by CommodityCategory referencedOn CommodityCategoryTags.category
    var tag by CommodityCategoryTags.tag
}

