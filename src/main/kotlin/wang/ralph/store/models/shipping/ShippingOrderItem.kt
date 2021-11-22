package wang.ralph.store.models.shipping

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ShippingOrderItems : UUIDTable("shipping_order_item") {
    val shippingOrder = reference("shipping_order_id", ShippingOrders)
    val skuId = uuid("sku_id")
    val skuAmount = decimal("sku_amount", 10, 2)
}

class ShippingOrderItem(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ShippingOrderItem>(ShippingOrderItems)

    var shippingOrder by ShippingOrder referencedOn ShippingOrderItems.shippingOrder
    var skuId by ShippingOrderItems.skuId
    var skuAmount by ShippingOrderItems.skuAmount
}
