package wang.ralph.store.models.shipping

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ReceiverContacts : UUIDTable("receiver_contact") {
    val shippingOrder = reference("shipping_order_id", ShippingOrders)
    val name = varchar("name", 32)
    val mobile = varchar("mobile", 32)
    val address = varchar("address", 255)
    val postcode = varchar("postcode", 32)
}

class ReceiverContact(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ReceiverContact>(ReceiverContacts)

    var shippingOrder by ShippingOrder referencedOn ReceiverContacts.shippingOrder
    var name by ReceiverContacts.name
    var mobile by ReceiverContacts.mobile
    var address by ReceiverContacts.address
    var postcode by ReceiverContacts.postcode
}
