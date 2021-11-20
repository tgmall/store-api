package wang.ralph.store.models.purchase

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import wang.ralph.store.models.commodity.Sku
import java.math.BigDecimal
import java.util.*

object PurchaseOrders : UUIDTable("purchase_order") {
    val userId = uuid("user_id")
}

class PurchaseOrder(id: EntityID<UUID>) : UUIDEntity(id) {
    fun addItem(sku: Sku, skuAmount: BigDecimal): PurchaseOrder {
        val order = this
        val purchaseOrderItem = PurchaseOrderItem.new {
            this.purchaseOrder = order
            this.skuId = sku.id.value
            this.actualPrice = sku.price!!
            this.actualAmount = skuAmount
        }
        SkuSnapshot.new {
            this.purchaseOrderItem = purchaseOrderItem
            this.name = sku.name
            this.description = sku.description
            this.unit = sku.unit
            this.price = sku.price
            this.imageUris = sku.images.map { it.imageUri }.joinToString(";")
            this.commodityName = sku.commodity.name
            this.commodityDescription = sku.commodity.description
            this.commodityTags = sku.commodity.tags.map { it.tag }.joinToString(";")
        }
        return order
    }

    companion object : UUIDEntityClass<PurchaseOrder>(PurchaseOrders) {
        fun create(userId: UUID): PurchaseOrder = PurchaseOrder.new {
            this.userId = userId
        }
    }

    val items by PurchaseOrderItem referrersOn PurchaseOrderItems.purchaseOrder
    var userId by PurchaseOrders.userId
}
