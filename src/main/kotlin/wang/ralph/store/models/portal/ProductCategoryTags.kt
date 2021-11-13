package wang.ralph.store.models.portal

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ProductCategoryTags : UUIDTable("product_category_tag") {
    val category = reference("group_id", ProductCategories)
    val tag = varchar("tag", 32)
}

class ProductCategoryTag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProductCategoryTag>(ProductCategoryTags)

    var category by ProductCategory referencedOn ProductCategoryTags.category
    var tag by ProductCategoryTags.tag
}

