package wang.ralph.store.application.shipping

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.purchase.PurchaseOrderQuery
import wang.ralph.store.models.shipping.ShippingOrder
import wang.ralph.store.models.shipping.ShippingSkuItem
import wang.ralph.store.plugins.userId
import java.util.*

class ShippingOrderMutation {
    fun createShippingOrder(
        dfe: DataFetchingEnvironment,
        purchaseOrderId: String,
        shipperId: String,
    ): ShippingOrderDto = transaction {
        val userId = dfe.userId
        val purchaseOrder = PurchaseOrderQuery().purchaseOrder(dfe, purchaseOrderId)
        val shippingOrder = ShippingOrder.create(
            userId = userId,
            purchaseOrderId = UUID.fromString(purchaseOrderId),
            shipperId = UUID.fromString(shipperId),
            address = purchaseOrder.receiverContact.address,
            skus = purchaseOrder.items.map {
                ShippingSkuItem(UUID.fromString(it.skuId), it.actualAmount)
            }
        )
        shippingOrder.toDto()
    }
}
