package wang.ralph.store.application.dtos.portal

import wang.ralph.store.models.portal.CommodityCategory

fun CommodityCategory.toDto(): CommodityCategoryDto =
    CommodityCategoryDto(
        id.toString(),
        name,
        children.map { it.toDto() },
        tags.map { it.toDto() },
        descentTags.map { it.toDto() },
        relatedTags.map { it.toDto() },
    )

data class CommodityCategoryDto(
    val id: String,
    val name: String,
    val children: List<CommodityCategoryDto>,
    val tags: List<CommodityCategoryTagDto>,
    val descentTags: List<CommodityCategoryTagDto>,
    val relatedTags: List<CommodityCategoryTagDto>,
)
