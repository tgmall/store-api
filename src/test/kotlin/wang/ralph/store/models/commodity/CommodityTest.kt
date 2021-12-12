package wang.ralph.store.models.commodity

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.recreateAllTables
import wang.ralph.store.setup.commodityA
import wang.ralph.store.setup.initCommodityData
import wang.ralph.store.setup.initTagData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommodityTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            recreateAllTables()
            initTagData()
            initCommodityData()
        }
    }

    @Test
    fun addAndRemoveTag() = transaction {
        assertEquals(listOf("tag1", "tag2"), commodityA.tags.map { it.tag })
        commodityA.removeTags(listOf("tag1"))
        assertEquals(listOf("tag2"), commodityA.tags.map { it.tag })
    }

    @Test
    fun findByTag() = transaction {
        val commodities = Commodity.findByTags(listOf("tag2"))
        assertEquals(listOf("commodityA"), commodities.map { it.name })
    }
}
