package wang.ralph.store.application.tag

import wang.ralph.store.models.tag.Tag
import wang.ralph.store.models.tag.TagType

fun Tag.toDto(): TagDto = when (type) {
    TagType.CommodityTag -> CommodityTagDto(tag)
    TagType.SubjectTag -> PersonTagDto(tag)
}

interface TagDto {
    val tag: String
}

class CommodityTagDto(override val tag: String) : TagDto
class PersonTagDto(override val tag: String) : TagDto
