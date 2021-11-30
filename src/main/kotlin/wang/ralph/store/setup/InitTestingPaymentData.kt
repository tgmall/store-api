package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.account.*
import java.math.BigDecimal

fun initTestingPaymentData() {
    SchemaUtils.drop(Payments)
    SchemaUtils.drop(PaymentGateways)
    SchemaUtils.create(PaymentGateways)
    SchemaUtils.create(Payments)

    val alipay = PaymentGateway.new {
        name = "支付宝"
        iconUri = "/files/alipay.svg"
        entryUrl = "/fake-payments/alipay"
    }
    Payment.new {
        userId = testingUser.id.value
        gateway = alipay
        voucherNum = "asdf1"
        amount = BigDecimal("100")
        status = PaymentStatus.Created
    }
}
