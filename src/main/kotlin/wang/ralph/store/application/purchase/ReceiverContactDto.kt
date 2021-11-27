package wang.ralph.store.application.purchase

import wang.ralph.store.models.purchase.ReceiverContact

fun ReceiverContact.toDto(): ReceiverContactDto = ReceiverContactDto(
    id = id.toString(),
    name = name,
    mobile = mobile,
    address = address,
    postcode = postcode
)

data class ReceiverContactDto(
    val id: String,
    val name: String,
    val mobile: String,
    val address: String,
    val postcode: String,
)
