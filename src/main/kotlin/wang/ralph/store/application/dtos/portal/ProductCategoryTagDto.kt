package wang.ralph.store.application.dtos.portal

import wang.ralph.store.models.portal.ProductCategoryTag

fun ProductCategoryTag.toDto(): ProductCategoryTagDto = ProductCategoryTagDto(id.toString(), tag)
data class ProductCategoryTagDto(val id: String, val tag: String)
