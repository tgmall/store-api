package wang.ralph.store.setup

import wang.ralph.store.models.account.Payment
import wang.ralph.store.models.account.PaymentGateway
import wang.ralph.store.models.account.PaymentStatus
import java.math.BigDecimal

fun initPaymentData() {
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
