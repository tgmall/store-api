package wang.ralph.store.application.cart

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.plugins.subject

class CartQuery {
    fun cart(dfe: DataFetchingEnvironment): CartDto = transaction {
        val userId = dfe.call.subject().userId
        val cart = Cart.ensureCart(userId)
        cart.toDto()
    }
}
