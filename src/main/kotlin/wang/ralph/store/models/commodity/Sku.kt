package wang.ralph.store.models.commodity

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object Skus : UUIDTable("sku") {
    val commodity = reference("commodity_id", Commodities)
    val name = varchar("name", 32)
    val description = text("description")
    val unit = varchar("unit", 32).default("个")
    val price = decimal("price", 10, 2).nullable()
}

@GraphQLDescription("SKU - 库存单元，通常对应于商品的一个特定规格")
class Sku(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Sku>(Skus) {
    }

    @GraphQLDescription("所属商品")
    var commodity by Commodity referencedOn Skus.commodity

    @GraphQLDescription("图片")
    val images by SkuImage referrersOn SkuImages.sku

    @GraphQLDescription("名称")
    var name: String by Skus.name

    @GraphQLDescription("描述")
    var description: String by Skus.description

    @GraphQLDescription("单位")
    var unit: String by Skus.unit

    @GraphQLDescription("价格")
    var price: BigDecimal? by Skus.price
}

