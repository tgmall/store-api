package wang.ralph.store.models.product

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingProductData
import wang.ralph.store.setup.productA
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ProductTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingProductData()
        }
    }

    @Test
    fun addAndRemoveTag() = transaction {
        assertEquals(listOf("tag1", "tag2"), productA.tags.map { it.tag })
        productA.removeTags("tag1")
        assertEquals(listOf("tag2"), productA.tags.map { it.tag })
    }

    @Test
    fun findByTag() = transaction {
        val products = Product.findByTags("tag2")
        assertEquals(listOf("productA"), products.map { it.name })
    }
}
