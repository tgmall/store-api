package wang.ralph.store.models.shipping

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object Shippers : UUIDTable("shipper") {
    val name = varchar("name", 255)
    val firstWeightFreight = decimal("first_weight_freight", 10, 2)
    val additionalWeightFreight = decimal("additional_weight_freight", 10, 2)
}

class Shipper(id: EntityID<UUID>) : UUIDEntity(id) {
    open fun calculateFreight(address: String, skus: Iterable<ShippingSkuItem>): BigDecimal {
        // TODO: 编写计算运费的算法
        return BigDecimal("10.00")
    }

    companion object : UUIDEntityClass<Shipper>(Shippers)

    var name by Shippers.name
    var firstWeightFreight by Shippers.firstWeightFreight
    var additionalWeightFreight by Shippers.additionalWeightFreight
}
