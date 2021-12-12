package wang.ralph.store.models.commodity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CommodityTags : UUIDTable("commodity_tag") {
    val commodity = reference("commodity_id", Commodities)
    val tag = varchar("tag", 32)
}

// 商品标签
class CommodityTag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityTag>(CommodityTags)

    // 所属商品
    var commodity by Commodity referencedOn CommodityTags.commodity

    // 标签
    var tag by CommodityTags.tag
}
