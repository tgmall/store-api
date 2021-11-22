package wang.ralph.store.application.shipping

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.application.purchase.PurchaseOrderQuery
import wang.ralph.store.models.shipping.ShippingOrder
import wang.ralph.store.models.shipping.ShippingSkuItem
import wang.ralph.store.plugins.subject
import java.util.*

class ShippingOrderMutation {
    fun createShippingOrder(
        dfe: DataFetchingEnvironment,
        purchaseOrderId: String,
        shipperId: String,
        receiverName: String,
        receiverMobile: String,
        address: String,
        postcode: String,
    ): ShippingOrderDto = transaction {
        val userId = dfe.call.subject().userId
        val purchaseOrder = PurchaseOrderQuery().purchaseOrder(dfe, purchaseOrderId)
        val shippingOrder = ShippingOrder.create(
            userId = userId,
            purchaseOrderId = UUID.fromString(purchaseOrderId),
            shipperId = UUID.fromString(shipperId),
            receiverName = receiverName,
            receiverMobile = receiverMobile,
            address = address,
            postcode = postcode,
            skus = purchaseOrder.items.map {
                ShippingSkuItem(
                    UUID.fromString(it.skuId),
                    it.actualAmount,
                )
            }
        )
        shippingOrder.toDto()
    }
}
