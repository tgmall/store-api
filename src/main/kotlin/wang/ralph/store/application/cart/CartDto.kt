package wang.ralph.store.application.cart

import wang.ralph.store.models.cart.Cart
import java.math.BigDecimal

fun Cart.toDto(): CartDto = CartDto(id.toString(), items.map { it.toDto() })
data class CartDto(val id: String, val items: List<CartItemDto>) {
    fun total(): BigDecimal = items.sumOf { it.total() }
}
