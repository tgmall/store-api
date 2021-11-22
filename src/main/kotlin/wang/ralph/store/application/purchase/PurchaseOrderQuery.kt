package wang.ralph.store.application.purchase

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.models.purchase.PurchaseOrder
import wang.ralph.store.models.purchase.PurchaseOrders
import wang.ralph.store.plugins.subject
import java.util.*

class PurchaseOrderQuery {
    fun purchaseOrders(dfe: DataFetchingEnvironment): List<PurchaseOrderDto> = transaction {
        val userId = dfe.call.subject().userId
        PurchaseOrder.find { PurchaseOrders.userId eq userId }.map { it.toDto() }
    }

    fun purchaseOrder(dfe: DataFetchingEnvironment, purchaseOrderId: String): PurchaseOrderDto = transaction {
        val userId = dfe.call.subject().userId
        PurchaseOrder.find { (PurchaseOrders.userId eq userId) and (PurchaseOrders.id eq UUID.fromString(purchaseOrderId)) }
            .first().toDto()
    }
}
