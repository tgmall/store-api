package wang.ralph.store.application.account

import wang.ralph.store.models.account.PaymentGateway

fun PaymentGateway.toDto(): PaymentGatewayDto = PaymentGatewayDto(
    id = id.toString(),
    name = name,
    iconUri = iconUri,
    entryUrl = entryUrl,
)

data class PaymentGatewayDto(val id: String, val name: String, val iconUri: String, val entryUrl: String)
