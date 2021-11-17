package wang.ralph.store.models.cart.models

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.cart.Cart
import wang.ralph.store.setup.initTestingCartData
import wang.ralph.store.setup.initTestingUserData
import wang.ralph.store.setup.setupTestingDb
import wang.ralph.store.setup.testingUser
import java.math.BigDecimal
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CartTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingUserData()
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
        cart.addItem(skuId, BigDecimal("10.00"))
        assertEquals(listOf(BigDecimal("10.00")), cart.items.map { it.skuAmount })
    }

    @Test
    fun removeItem() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        val skuId = UUID.randomUUID()
        cart.removeItem(skuId, BigDecimal("10.00"))
        assertEquals(listOf(BigDecimal("0.00")), cart.items.map { it.skuAmount })
        cart.addItem(skuId, BigDecimal("10.00"))
        cart.removeItem(skuId, BigDecimal("5.00"))
        assertEquals(listOf(BigDecimal("5.00")), cart.items.map { it.skuAmount })
    }

    @Test
    fun purge() = transaction {
        val cart = Cart.ensureCart(testingUser.subjectId)
        val skuId = UUID.randomUUID()
        cart.addItem(skuId, BigDecimal("10.00"))
        cart.removeItem(skuId, BigDecimal("10.00"))
        assertEquals(listOf(BigDecimal("0.00")), cart.items.map { it.skuAmount })
        cart.purge()
        assertEquals(emptyList(), cart.items.toList())
    }
}
