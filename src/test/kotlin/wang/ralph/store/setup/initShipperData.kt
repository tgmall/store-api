package wang.ralph.store.setup

import wang.ralph.store.models.shipping.Shipper
import java.math.BigDecimal

fun initShipperData() {
    Shipper.new {
        name = "顺丰"
        firstWeightFreight = BigDecimal("16.00")
        additionalWeightFreight = BigDecimal("4.00")
    }
}
