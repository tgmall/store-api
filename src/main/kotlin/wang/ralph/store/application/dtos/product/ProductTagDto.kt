package wang.ralph.store.application.dtos.product

import wang.ralph.store.models.product.ProductTag

fun ProductTag.toDto() = ProductTagDto(id.toString(), tag)
data class ProductTagDto(val id: String, val tag: String)
