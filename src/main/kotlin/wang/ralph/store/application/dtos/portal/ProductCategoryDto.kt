package wang.ralph.store.application.dtos.portal

import wang.ralph.store.models.portal.ProductCategory

fun ProductCategory.toDto(): ProductCategoryDto =
    ProductCategoryDto(id.toString(), name, children.map { it.toDto() }, tags.map { it.toDto() })

data class ProductCategoryDto(
    val id: String,
    val name: String,
    val children: List<ProductCategoryDto>,
    val tags: List<ProductCategoryTagDto>,
)
