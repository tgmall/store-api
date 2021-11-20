package wang.ralph.store.models.commodity

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import java.util.*

object Commodities : UUIDTable("commodity") {
    val name = varchar("name", 255)
    val description = text("description").clientDefault { "" }
}

@GraphQLDescription("商品")
class Commodity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Commodity>(Commodities) {
        @GraphQLDescription("根据标签查找商品")
        fun findByTags(tags: List<String>): SizedIterable<Commodity> {
            return find {
                exists(
                    CommodityTags.select {
                        (CommodityTags.commodity eq Commodities.id) and (CommodityTags.tag inList tags.distinct())
                    }
                )
            }
        }
    }

    @GraphQLDescription("商品名称")
    var name: String by Commodities.name

    @GraphQLDescription("商品描述")
    var description: String by Commodities.description

    @GraphQLDescription("本商品下的所有 SKU 列表")
    val skus by Sku referrersOn Skus.commodity

    @GraphQLDescription("本商品的标签")
    val tags by CommodityTag referrersOn CommodityTags.commodity

    @GraphQLDescription("添加标签")
    fun addTags(tags: Iterable<String>): Commodity {
        val commodity = this
        tags.forEach { tag ->
            CommodityTag.new {
                this.commodity = commodity
                this.tag = tag
            }
        }
        return commodity
    }

    @GraphQLDescription("移除标签")
    fun removeTags(tags: Iterable<String>): Commodity {
        CommodityTags.deleteWhere {
            CommodityTags.tag inList tags.toList()
        }
        return this
    }
}
