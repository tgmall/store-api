package wang.ralph.store.models.tag

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

// 标签类型
enum class TagType {
    // 商品标签
    CommodityTag,

    // 行为主体标签
    SubjectTag,
}

object Tags : UUIDTable("tag") {
    val type = enumerationByName("type", 32, TagType::class)
    val tag = varchar("tag", 32)
    val description = text("description").clientDefault { "" }
}

// 标签
class Tag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Tag>(Tags) {
        // 生成新的商品标签
        fun newCommodityTag(tag: String, description: String = "") = new {
            this.type = TagType.CommodityTag
            this.tag = tag
            this.description = description
        }

        // 生成新的行为主体标签
        fun newSubjectTag(tag: String, description: String = "") = new {
            this.type = TagType.SubjectTag
            this.tag = tag
            this.description = description
        }
    }

    // 类型
    var type by Tags.type

    // 名称
    var tag by Tags.tag

    // 标签说明
    var description by Tags.description
}

