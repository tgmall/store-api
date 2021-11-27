package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.shipping.Shipper
import wang.ralph.store.models.shipping.Shippers
import wang.ralph.store.models.shipping.ShippingOrderItems
import wang.ralph.store.models.shipping.ShippingOrders
import java.math.BigDecimal

fun initTestingShippingOrderData() {
    SchemaUtils.drop(ShippingOrderItems)
    SchemaUtils.drop(ShippingOrders)
    SchemaUtils.drop(Shippers)
    SchemaUtils.create(Shippers)
    SchemaUtils.create(ShippingOrders)
    SchemaUtils.create(ShippingOrderItems)

    Shipper.new {
        name = "顺丰"
        firstWeightFreight = BigDecimal("16.00")
        additionalWeightFreight = BigDecimal("4.00")
    }
}
