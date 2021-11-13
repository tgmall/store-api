package wang.ralph.store.models.portal

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductCategories : UUIDTable("product_category") {
    val name = varchar("name", 32)
    val parent = reference("parent_id", ProductCategories).nullable()
}

class ProductCategory(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProductCategory>(ProductCategories)

    var name: String by ProductCategories.name
    var parent by ProductCategory optionalReferencedOn ProductCategories.parent
    val children by ProductCategory optionalReferrersOn ProductCategories.parent
    val tags by ProductCategoryTag referrersOn ProductCategoryTags.category
}
