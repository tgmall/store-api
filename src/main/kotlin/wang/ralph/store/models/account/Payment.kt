package wang.ralph.store.models.account

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.math.BigDecimal
import java.util.*

// 支付状态
enum class PaymentStatus {
    // 已创建
    Created,

    // 正在支付
    Paying,

    // 已支付
    Paid,
}

object Payments : UUIDTable("payment") {
    val userId = uuid("user_id")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val voucherNum = varchar("voucher_num", 64).nullable()
    val gateway = reference("gateway_id", PaymentGateways)
    val amount = decimal("amount", 10, 2)
    val reason = varchar("reason", 255).default("")
    val status = enumerationByName("status", 32, PaymentStatus::class)
}

// 支付记录
class Payment(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Payment>(Payments) {
        fun pay(
            userId: UUID,
            paymentGatewayId: UUID,
            amount: BigDecimal,
        ): Payment {
            return Payment.new {
                this.userId = userId
                this.gateway = PaymentGateway.get(paymentGatewayId)
                this.status = PaymentStatus.Paid
                this.amount = amount
            }
        }
    }

    // 支付者用户
    var userId by Payments.userId

    // 创建时间
    var createdAt by Payments.createdAt

    // 支付网关
    var gateway by PaymentGateway referencedOn Payments.gateway

    // 支付网关的凭证号
    var voucherNum by Payments.voucherNum

    // 金额
    var amount by Payments.amount

    // 支付理由
    var reason by Payments.reason

    // 状态
    var status by Payments.status
}
