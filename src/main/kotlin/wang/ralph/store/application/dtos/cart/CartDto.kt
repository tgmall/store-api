package wang.ralph.store.application.dtos.cart

import wang.ralph.store.models.cart.Cart

fun Cart.toDto(): CartDto = CartDto(id.toString(), items.map { it.toDto() })
data class CartDto(val id: String, val items: List<CartItemDto>)
