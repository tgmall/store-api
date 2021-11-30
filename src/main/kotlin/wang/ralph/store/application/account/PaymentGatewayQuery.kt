package wang.ralph.store.application.account

import wang.ralph.store.models.account.PaymentGateway

class PaymentGatewayQuery {
    fun paymentGateways(): List<PaymentGatewayDto> {
        return PaymentGateway.all().map { it.toDto() }
    }
}
