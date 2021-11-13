package wang.ralph.store.application.dtos

import wang.ralph.store.models.tag.Tag
import wang.ralph.store.models.tag.TagType

fun Tag.toDto(): TagDto = when (type) {
    TagType.CommodityTag -> CommodityTagDto(name)
    TagType.PersonTag -> PersonTagDto(name)
}

interface TagDto {
    val tag: String
}

class CommodityTagDto(override val tag: String) : TagDto
class PersonTagDto(override val tag: String) : TagDto
