package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.shipping.Shipper
import wang.ralph.store.models.shipping.Shippers
import java.math.BigDecimal

fun initTestingShipperData() {
    SchemaUtils.drop(Shippers)
    SchemaUtils.create(Shippers)

    Shipper.new {
        name = "顺丰"
        firstWeightFreight = BigDecimal("16.00")
        additionalWeightFreight = BigDecimal("4.00")
    }
}
