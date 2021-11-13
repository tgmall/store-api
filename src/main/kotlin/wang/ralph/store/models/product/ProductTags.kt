package wang.ralph.store.models.product

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductTags : UUIDTable("product_tag") {
    val product = reference("product_id", Products)
    val tag = varchar("tag", 32)
}

class ProductTag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProductTag>(ProductTags)

    var product by Product referencedOn ProductTags.product
    var tag by ProductTags.tag

}
