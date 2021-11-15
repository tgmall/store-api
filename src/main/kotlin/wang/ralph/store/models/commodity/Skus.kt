package wang.ralph.store.models.commodity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object Skus : UUIDTable("sku") {
    val commodity = reference("commodity_id", Commodities)
    val name = varchar("name", 32)
    val description = text("description")
    val unit = varchar("unit", 32).default("个")
    val price = decimal("price", 10, 2).nullable()
}

class Sku(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Sku>(Skus) {
    }

    var commodity by Commodity referencedOn Skus.commodity
    val images by SkuImage referrersOn SkuImages.sku
    var name: String by Skus.name
    var description: String by Skus.description
    var unit: String by Skus.unit
    var price: BigDecimal? by Skus.price
}

