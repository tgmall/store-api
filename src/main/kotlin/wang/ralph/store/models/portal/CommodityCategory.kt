package wang.ralph.store.models.portal

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

// 商品分类
class CommodityCategory(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CommodityCategory>(CommodityCategories) {
        // 商品分类树
        fun tree(): SizedIterable<CommodityCategory> {
            return find { CommodityCategories.parent.isNull() }
        }
    }

    // 分类名
    var name: String by CommodityCategories.name

    // 父级分类
    var parent by CommodityCategory optionalReferencedOn CommodityCategories.parent

    // 子级分类
    val children by CommodityCategory optionalReferrersOn CommodityCategories.parent

    // 相关标签（具有下列分类标签的商品会被显示在此分类下）
    val tags by CommodityCategoryTag referrersOn CommodityCategoryTags.category
}
