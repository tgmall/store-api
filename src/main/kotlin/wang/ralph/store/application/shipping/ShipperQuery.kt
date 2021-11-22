package wang.ralph.store.application.shipping

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.shipping.Shipper

class ShipperQuery {
    fun shippers(): List<ShipperDto> = transaction {
        Shipper.all().map { it.toDto() }
    }
}
