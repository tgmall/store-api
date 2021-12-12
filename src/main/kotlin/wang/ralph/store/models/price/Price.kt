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
    val skuAmount = decimal("sku_amount", 10, 2)
    val validFrom = timestamp("validFrom")
    val validTo = timestamp("validTo")
}

// 价目条目
class Price(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Price>(Prices) {
        // 寻找特定 SKU 在特定时间的价目
        fun findPrice(skuId: UUID, now: Instant = Instant.now()): Price? {
            return find {
                (Prices.skuId eq skuId) and
                        (Prices.validFrom lessEq now) and (Prices.validTo greater now)
            }.maxByOrNull { it.validFrom }
        }
    }

    // SKU 的 ID
    var skuId by Prices.skuId

    // SKU 的数量
    var skuAmount by Prices.skuAmount

    // 生效时间（闭区间）
    var validFrom by Prices.validFrom

    // 失效时间（开区间）
    var validTo by Prices.validTo
}
