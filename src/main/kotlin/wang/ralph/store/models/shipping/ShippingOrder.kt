package wang.ralph.store.models.shipping

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

// 运单状态
enum class ShippingOrderStatusEnum {
    // 刚刚创建
    Created,

    // 已支付
    Paid,

    // 待发货
    Shipping,

    // 已发货
    Shipped,

    // 已签收
    Signed,
}

object ShippingOrders : UUIDTable("shipping_order") {
    val userId = uuid("user_id")
    val purchaseOrderId = uuid("purchase_order_id")
    val shipper = reference("shipper_id", Shippers)
    val serialNumber = varchar("serial_number", 64)
    val freight = decimal("freight", 10, 2)
    val status = enumerationByName("status", 32, ShippingOrderStatusEnum::class)
}

// 运单
class ShippingOrder(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ShippingOrder>(ShippingOrders) {
        fun create(
            userId: UUID,
            purchaseOrderId: UUID,
            shipperId: UUID,
            address: String,
            skus: Iterable<ShippingSkuItem>,
        ): ShippingOrder {
            val shippingOrder = ShippingOrder.new {
                this.userId = userId
                this.serialNumber = generateSerialNumber()
                this.purchaseOrderId = purchaseOrderId
                this.shipper = Shipper.get(shipperId)
                this.freight = shipper.calculateFreight(address, skus)
                this.status = ShippingOrderStatusEnum.Created
            }
            skus.forEach {
                ShippingOrderItem.new {
                    this.shippingOrder = shippingOrder
                    this.skuId = it.skuId
                    this.skuAmount = it.skuAmount
                }
            }
            return shippingOrder
        }

        private fun generateSerialNumber(): String {
            return UUID.randomUUID().toString()
        }
    }

    // 所属用户的 ID
    var userId by ShippingOrders.userId

    // 要发货的订购单
    var purchaseOrderId by ShippingOrders.purchaseOrderId

    // 运单内容
    val items by ShippingOrderItem referrersOn ShippingOrderItems.shippingOrder

    // 承运人
    var shipper by Shipper referencedOn ShippingOrders.shipper

    // 运单号
    var serialNumber by ShippingOrders.serialNumber

    // 运价
    var freight by ShippingOrders.freight

    // 状态
    var status by ShippingOrders.status
}

data class ShippingSkuItem(
    val skuId: UUID,
    val skuAmount: BigDecimal,
)
