package wang.ralph.store.models.purchase

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object SkuSnapshots : UUIDTable("sku_snapshot") {
    val purchaseOrderItem = reference("purchase_order_item_id", PurchaseOrderItems)
    val name = varchar("name", 32)
    val description = text("description").clientDefault { "" }
    val unit = varchar("unit", 32).default("个")
    val price = decimal("price", 10, 2).nullable()
    val imageUris = text("image_uris").clientDefault { "" }
    val commodityName = varchar("commodity_name", 32)
    val commodityDescription = text("commodity_description").clientDefault { "" }
    val commodityTags = text("commodity_tags").clientDefault { "" }
}

@GraphQLDescription("SKU 快照，用于记录当前定价等信息，以便回溯")
class SkuSnapshot(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SkuSnapshot>(SkuSnapshots)

    @GraphQLDescription("所属的订单项")
    var purchaseOrderItem by PurchaseOrderItem referencedOn SkuSnapshots.purchaseOrderItem

    @GraphQLDescription("名称")
    var name: String by SkuSnapshots.name

    @GraphQLDescription("描述")
    var description: String by SkuSnapshots.description

    @GraphQLDescription("单位")
    var unit: String by SkuSnapshots.unit

    @GraphQLDescription("价格")
    var price: BigDecimal? by SkuSnapshots.price

    @GraphQLDescription("图片")
    var imageUris: String by SkuSnapshots.imageUris

    @GraphQLDescription("所属商品名称")
    var commodityName: String by SkuSnapshots.commodityName

    @GraphQLDescription("所属商品描述")
    var commodityDescription: String by SkuSnapshots.commodityDescription

    @GraphQLDescription("标签")
    var commodityTags: String by SkuSnapshots.commodityTags
}