package wang.ralph.store.product.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Products : UUIDTable("product") {
    val name = varchar("name", 255)
    val description = text("description")
}

class Product(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Product>(Products) {
    }

    var name: String by Products.name
    var description: String by Products.description
    val skus by Sku referrersOn Skus.product
    fun toDto(): ProductDto = ProductDto(id.toString(), name, skus.map { it.toDto() })
}

data class ProductDto(val id: String, val name: String, val skus: List<SkuDto>)
