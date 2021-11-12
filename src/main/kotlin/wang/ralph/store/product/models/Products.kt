package wang.ralph.store.product.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import java.util.*

object Products : UUIDTable("product") {
    val name = varchar("name", 255)
    val description = text("description")
}

class Product(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Product>(Products) {
        fun findByTags(vararg tags: String): SizedIterable<Product> {
            return find {
                exists(
                    ProductTags.select {
                        (ProductTags.product eq Products.id) and (ProductTags.tag inList tags.toList())
                    }
                )
            }
        }
    }

    var name: String by Products.name
    var description: String by Products.description
    val skus by Sku referrersOn Skus.product
    val tags by ProductTag referrersOn ProductTags.product
    fun addTags(vararg tags: String): Product {
        val product = this
        tags.forEach { tag ->
            ProductTag.new {
                this.product = product
                this.tag = tag
            }
        }
        return product
    }

    fun removeTags(vararg tags: String): Product {
        ProductTags.deleteWhere {
            ProductTags.tag inList tags.toList()
        }
        return this
    }

    fun toDto(): ProductDto = ProductDto(id.toString(), name, skus.map { it.toDto() })
}

data class ProductDto(val id: String, val name: String, val skus: List<SkuDto>)
