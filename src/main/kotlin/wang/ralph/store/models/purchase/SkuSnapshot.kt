package wang.ralph.store.models.purchase

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
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

// SKU 快照，用于记录当前定价等信息，以便回溯
class SkuSnapshot(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SkuSnapshot>(SkuSnapshots)

    // 所属的订单项
    var purchaseOrderItem by PurchaseOrderItem referencedOn SkuSnapshots.purchaseOrderItem

    // 名称
    var name by SkuSnapshots.name

    // 描述
    var description by SkuSnapshots.description

    // 单位
    var unit by SkuSnapshots.unit

    // 价格
    var price by SkuSnapshots.price

    // 图片
    var imageUris by SkuSnapshots.imageUris

    // 所属商品名称
    var commodityName by SkuSnapshots.commodityName

    // 所属商品描述
    var commodityDescription by SkuSnapshots.commodityDescription

    // 标签
    var commodityTags by SkuSnapshots.commodityTags
}
