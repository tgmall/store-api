package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.shipping.*
import java.math.BigDecimal

fun initTestingShippingOrderData() {
    SchemaUtils.drop(ReceiverContacts)
    SchemaUtils.drop(ShippingOrderItems)
    SchemaUtils.drop(ShippingOrders)
    SchemaUtils.drop(Shippers)
    SchemaUtils.create(Shippers)
    SchemaUtils.create(ShippingOrders)
    SchemaUtils.create(ShippingOrderItems)
    SchemaUtils.create(ReceiverContacts)

    Shipper.new {
        name = "顺丰"
        firstWeightFreight = BigDecimal("16.00")
        additionalWeightFreight = BigDecimal("4.00")
    }
}
