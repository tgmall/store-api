package wang.ralph.store.models.product

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingProductData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest

internal class SkuTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingProductData()
        }
    }
}

