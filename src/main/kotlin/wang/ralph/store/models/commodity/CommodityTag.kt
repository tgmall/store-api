package wang.ralph.store.models.commodity

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object CommodityTags : UUIDTable("commodity_tag") {
    val commodity = reference("commodity_id", Commodities)
    val tag = varchar("tag", 32)
}

@GraphQLDescription("商品标签")
class CommodityTag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityTag>(CommodityTags)

    @GraphQLDescription("所属商品")
    var commodity by Commodity referencedOn CommodityTags.commodity

    @GraphQLDescription("标签")
    var tag by CommodityTags.tag
}
