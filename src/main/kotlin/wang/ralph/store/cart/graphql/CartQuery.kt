package wang.ralph.store.cart.graphql

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.cart.models.Cart
import wang.ralph.store.cart.models.CartDto
import wang.ralph.store.plugins.subject

class CartQuery {
    fun cart(dfe: DataFetchingEnvironment): CartDto = transaction {
        val ownerId = dfe.call.subject().subjectId
        val cart = Cart.ensureCart(ownerId)
        cart.toDto()
    }
}
