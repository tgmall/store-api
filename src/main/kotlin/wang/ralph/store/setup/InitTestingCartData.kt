package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.cart.CartItems
import wang.ralph.store.models.cart.Carts

fun initTestingCartData() {
    SchemaUtils.create(Carts)
    SchemaUtils.create(CartItems)
    CartItems.deleteAll()
    Carts.deleteAll()
}
