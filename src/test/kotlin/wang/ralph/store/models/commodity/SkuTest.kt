package wang.ralph.store.models.commodity

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingCommodityData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest

internal class SkuTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingCommodityData()
        }
    }
}

