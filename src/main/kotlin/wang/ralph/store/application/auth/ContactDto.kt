package wang.ralph.store.application.auth

import wang.ralph.store.models.auth.Contact

fun Contact.toDto(): ContactDto = ContactDto(
    id = id.toString(),
    name = name,
    mobile = mobile,
    address = address,
    postcode = postcode
)

data class ContactDto(
    val id: String,
    val name: String,
    val mobile: String,
    val address: String,
    val postcode: String,
)
