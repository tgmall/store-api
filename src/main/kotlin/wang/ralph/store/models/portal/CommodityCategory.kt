package wang.ralph.store.models.portal

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

object CommodityCategories : UUIDTable("commodity_category") {
    val name = varchar("name", 32)
    val parent = reference("parent_id", CommodityCategories).nullable()
}

@GraphQLDescription("商品分类")
class CommodityCategory(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityCategory>(CommodityCategories) {
        @GraphQLDescription("商品分类树")
        fun tree(): SizedIterable<CommodityCategory> {
            return find { CommodityCategories.parent.isNull() }
        }
    }

    @GraphQLDescription("分类名")
    var name: String by CommodityCategories.name

    @GraphQLDescription("父级分类")
    var parent by CommodityCategory optionalReferencedOn CommodityCategories.parent

    @GraphQLDescription("子级分类")
    val children by CommodityCategory optionalReferrersOn CommodityCategories.parent

    @GraphQLDescription("相关标签（具有下列分类标签的商品会被显示在此分类下）")
    val tags by CommodityCategoryTag referrersOn CommodityCategoryTags.category
}
