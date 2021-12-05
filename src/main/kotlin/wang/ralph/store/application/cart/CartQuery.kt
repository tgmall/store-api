package wang.ralph.store.application.cart

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.plugins.userId

class CartQuery {
    fun cart(dfe: DataFetchingEnvironment): CartDto = transaction {
        val userId = dfe.userId
        val cart = Cart.ensureCart(userId)
        cart.toDto()
    }
}
