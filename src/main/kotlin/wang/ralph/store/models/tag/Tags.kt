package wang.ralph.store.models.tag

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

enum class TagType {
    ProductTag,
    PersonTag,
}

object Tags : UUIDTable("tag") {
    val tag = varchar("tag", 32)
    val type = enumerationByName("type", 32, TagType::class)
}

class Tag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Tag>(Tags) {
        fun newProductTag(tag: String) = new {
            this.type = TagType.ProductTag
            this.name = tag
        }

        fun newPersonTag(tag: String) = new {
            this.type = TagType.PersonTag
            this.name = tag
        }
    }

    var name: String by Tags.tag
    var type: TagType by Tags.type

}
