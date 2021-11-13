package wang.ralph.store.application.dtos.cart

import wang.ralph.store.models.cart.CartItem
import java.util.*

fun CartItem.toDto(): CartItemDto = CartItemDto(skuId, amount)
data class CartItemDto(val skuId: UUID, val amount: Int)
