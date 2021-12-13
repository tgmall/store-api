package wang.ralph.store.application.shipping

import wang.ralph.store.models.shipping.ShippingOrder
import wang.ralph.store.models.shipping.ShippingOrderStatus
import java.math.BigDecimal

fun ShippingOrder.toDto(): ShippingOrderDto = ShippingOrderDto(
    id = id.toString(),
    purchaseOrderId = purchaseOrderId.toString(),
    items = items.map { it.toDto() },
    shipper = shipper.toDto(),
    serialNumber = serialNumber,
    freight = freight,
    status = status
)

data class ShippingOrderDto(
    val id: String,
    // 要发货的订购单
    val purchaseOrderId: String,
    // 运单内容
    val items: List<ShippingOrderItemDto>,

    // 承运人
    val shipper: ShipperDto,

    // 运单号
    val serialNumber: String,

    // 运价
    val freight: BigDecimal,

    // 状态
    val status: ShippingOrderStatus,
) {
    val amount get() = freight
}
