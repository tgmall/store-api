package wang.ralph.store.models.cart.models

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.setup.initTestingCartData
import wang.ralph.store.setup.setupTestingDb
import wang.ralph.store.setup.testingUser
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CartTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingCartData()
        }
    }

    @Test
    fun getItems() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        assertEquals(emptyList(), cart.items.toList())
    }

    @Test
    fun addItem() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        val skuId = UUID.randomUUID()
        cart.addItem(skuId, 10)
        assertEquals(listOf(10), cart.items.map { it.amount })
    }

    @Test
    fun removeItem() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        val skuId = UUID.randomUUID()
        cart.removeItem(skuId, 10)
        assertEquals(listOf(0), cart.items.map { it.amount })
        cart.addItem(skuId, 10)
        cart.removeItem(skuId, 5)
        assertEquals(listOf(5), cart.items.map { it.amount })
    }

    @Test
    fun purge() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        val skuId = UUID.randomUUID()
        cart.addItem(skuId, 10)
        cart.removeItem(skuId, 10)
        assertEquals(listOf(0), cart.items.map { it.amount })
        cart.purge()
        assertEquals(emptyList(), cart.items.toList())
    }
}
