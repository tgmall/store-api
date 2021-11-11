package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.cart.models.CartItems
import wang.ralph.store.cart.models.Carts

fun initTestingCartData() {
    SchemaUtils.create(Carts)
    SchemaUtils.create(CartItems)
    CartItems.deleteAll()
    Carts.deleteAll()
}
