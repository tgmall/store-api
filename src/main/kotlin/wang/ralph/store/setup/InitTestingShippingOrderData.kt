package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.shipping.ReceiverContacts
import wang.ralph.store.models.shipping.ShippingOrderItems
import wang.ralph.store.models.shipping.ShippingOrders

fun initTestingShippingOrderData() {
    SchemaUtils.drop(ReceiverContacts)
    SchemaUtils.drop(ShippingOrderItems)
    SchemaUtils.drop(ShippingOrders)
    SchemaUtils.create(ShippingOrders)
    SchemaUtils.create(ShippingOrderItems)
    SchemaUtils.create(ReceiverContacts)
}
