package wang.ralph.store.models.tag

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@GraphQLDescription("标签类型")
enum class TagType {
    @GraphQLDescription("商品标签")
    CommodityTag,

    @GraphQLDescription("行为主体标签")
    SubjectTag,
}

object Tags : UUIDTable("tag") {
    val type = enumerationByName("type", 32, TagType::class)
    val tag = varchar("tag", 32)
    val description = text("description").clientDefault { "" }
}

@GraphQLDescription("标签")
class Tag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Tag>(Tags) {
        @GraphQLDescription("生成新的商品标签")
        fun newCommodityTag(tag: String, description: String = "") = new {
            this.type = TagType.CommodityTag
            this.tag = tag
            this.description = description
        }

        @GraphQLDescription("生成新的行为主体标签")
        fun newSubjectTag(tag: String, description: String = "") = new {
            this.type = TagType.SubjectTag
            this.tag = tag
            this.description = description
        }
    }

    @GraphQLDescription("类型")
    var type: TagType by Tags.type

    @GraphQLDescription("名称")
    var tag: String by Tags.tag

    @GraphQLDescription("标签说明")
    var description by Tags.description
}

