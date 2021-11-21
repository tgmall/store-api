package wang.ralph.store.application.portal

import wang.ralph.store.models.portal.CommodityCategory

fun CommodityCategory.toDto(): CommodityCategoryDto =
    CommodityCategoryDto(
        id.toString(),
        name,
        children.map { it.toDto() },
        tags.map { it.toDto() },
    )

data class CommodityCategoryDto(
    val id: String,
    val name: String,
    val children: List<CommodityCategoryDto> = emptyList(),
    val tags: List<CommodityCategoryTagDto> = emptyList(),
) {
    fun descentTags(): List<CommodityCategoryTagDto> {
        return children.flatMap { it.tags + it.descentTags() }
    }

    fun relatedTags(): List<CommodityCategoryTagDto> {
        return tags + children.flatMap { it.tags + it.descentTags() }
    }
}
