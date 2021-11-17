package wang.ralph.store.models.price

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.timestamp
import org.jetbrains.exposed.sql.and
import java.time.Instant
import java.util.*

object Prices : UUIDTable("price") {
    val skuId = uuid("sku_id")
    val amount = decimal("amount", 10, 2)
    val validFrom = timestamp("validFrom")
    val validTo = timestamp("validTo")
}

class Price(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Price>(Prices) {
        fun findPrice(skuId: UUID, now: Instant = Instant.now()): Price? {
            return find {
                (Prices.skuId eq skuId) and
                        (Prices.validFrom lessEq now) and (Prices.validTo greater now)
            }.maxByOrNull { it.validFrom }
        }
    }

    var skuId by Prices.skuId
    var amount by Prices.amount
    var validFrom by Prices.validFrom
    var validTo by Prices.validTo
}
