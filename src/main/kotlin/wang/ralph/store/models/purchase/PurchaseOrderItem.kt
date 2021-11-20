package wang.ralph.store.models.purchase

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object PurchaseOrderItems : UUIDTable("purchase_order_item") {
    val purchaseOrder = reference("purchase_order_id", PurchaseOrders)
    val skuId = uuid("sku_id")
    val actualPrice = decimal("actual_price", 10, 2)
    val actualAmount = decimal("actual_amount", 10, 2)
}

class PurchaseOrderItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PurchaseOrderItem>(PurchaseOrderItems)

    var purchaseOrder by PurchaseOrder referencedOn PurchaseOrderItems.purchaseOrder
    val skuSnapshot by SkuSnapshot backReferencedOn SkuSnapshots.purchaseOrderItem
    var skuId by PurchaseOrderItems.skuId
    var actualPrice by PurchaseOrderItems.actualPrice
    var actualAmount by PurchaseOrderItems.actualAmount
}
