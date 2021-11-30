package wang.ralph.store.models.account

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object PaymentGateways : UUIDTable("payment_gateway") {
    val name = varchar("name", 255)
    val iconUri = varchar("icon_uri", 255)
    val entryUrl = varchar("entry_url", 255)
}

class PaymentGateway(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PaymentGateway>(PaymentGateways)

    var name by PaymentGateways.name
    var iconUri by PaymentGateways.iconUri
    var entryUrl by PaymentGateways.entryUrl
}
