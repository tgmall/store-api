package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.purchase.PurchaseOrderItems
import wang.ralph.store.models.purchase.PurchaseOrders
import wang.ralph.store.models.purchase.ReceiverContacts
import wang.ralph.store.models.purchase.SkuSnapshots

fun initTestingPurchaseOrderData() {
    SchemaUtils.drop(ReceiverContacts)
    SchemaUtils.drop(SkuSnapshots)
    SchemaUtils.drop(PurchaseOrderItems)
    SchemaUtils.drop(PurchaseOrders)
    SchemaUtils.create(PurchaseOrders)
    SchemaUtils.create(PurchaseOrderItems)
    SchemaUtils.create(SkuSnapshots)
    SchemaUtils.create(ReceiverContacts)
}
