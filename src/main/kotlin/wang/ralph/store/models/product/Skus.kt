package wang.ralph.store.models.product

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Skus : UUIDTable("sku") {
    val product = reference("product_id", Products)
    val name = varchar("name", 32)
    val description = text("description")
}

class Sku(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Sku>(Skus) {
    }

    var product by Product referencedOn Skus.product
    val images by SkuImage referrersOn SkuImages.sku
    var name: String by Skus.name
    var description: String by Skus.description

}
