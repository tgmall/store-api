package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.cart.CartItems
import wang.ralph.store.models.cart.Carts

fun initTestingCartData() {
    SchemaUtils.drop(CartItems)
    SchemaUtils.drop(Carts)
    SchemaUtils.create(Carts)
    SchemaUtils.create(CartItems)
}
