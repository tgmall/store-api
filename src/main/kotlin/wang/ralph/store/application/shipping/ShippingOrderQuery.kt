package wang.ralph.store.application.shipping

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.shipping.ShippingOrder
import wang.ralph.store.models.shipping.ShippingOrders
import wang.ralph.store.plugins.userId
import java.util.*

class ShippingOrderQuery {
    fun shippingOrders(dfe: DataFetchingEnvironment): List<ShippingOrderDto> = transaction {
        val userId = dfe.userId
        ShippingOrder.find { ShippingOrders.userId eq userId }.map { it.toDto() }
    }

    fun shippingOrder(dfe: DataFetchingEnvironment, purchaseOrderId: String): ShippingOrderDto = transaction {
        val userId = dfe.userId
        ShippingOrder.find {
            (ShippingOrders.userId eq userId) and (ShippingOrders.purchaseOrderId eq UUID.fromString(purchaseOrderId))
        }.first().toDto()
    }
}
