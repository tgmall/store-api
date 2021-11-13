package wang.ralph.store.application.dtos.product

import wang.ralph.store.models.product.Product

fun Product.toDto(): ProductDto = ProductDto(id.toString(), name, skus.map { it.toDto() })
data class ProductDto(val id: String, val name: String, val skus: List<SkuDto>)
