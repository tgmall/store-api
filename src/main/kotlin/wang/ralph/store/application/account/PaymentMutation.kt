package wang.ralph.store.application.account

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.purchase.PurchaseOrderQuery
import wang.ralph.store.application.shipping.ShippingOrderQuery
import wang.ralph.store.models.account.Payment
import wang.ralph.store.plugins.userId
import java.util.*

class PaymentMutation {
    fun pay(
        dfe: DataFetchingEnvironment,
        paymentGatewayId: String,
        purchaseOrderId: String,
        shippingOrderId: String,
    ): PaymentDto = transaction {
        val userId = dfe.userId
        val purchaseOrder = PurchaseOrderQuery().purchaseOrder(dfe, purchaseOrderId)
        val shippingOrder = ShippingOrderQuery().shippingOrder(dfe, shippingOrderId)
        Payment.pay(
            userId = userId,
            paymentGatewayId = UUID.fromString(paymentGatewayId),
            amount = purchaseOrder.amount + shippingOrder.amount
        ).toDto()
    }
}
