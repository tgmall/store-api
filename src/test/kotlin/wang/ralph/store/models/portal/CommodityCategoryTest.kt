package wang.ralph.store.models.portal

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.portal.toDto
import wang.ralph.store.setup.commodityCategoryA
import wang.ralph.store.setup.initTestingCommodityCategoryData
import wang.ralph.store.setup.initTestingCommodityData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommodityCategoryTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingCommodityData()
            initTestingCommodityCategoryData()
        }
    }

    @Test
    fun getDescentTags() = transaction {
        assertEquals(listOf("tag2", "tag3"), commodityCategoryA.toDto().descentTags().map { it.tag })
    }

    @Test
    fun getRelatedTags() = transaction {
        assertEquals(listOf("tag1", "tag2", "tag3"), commodityCategoryA.toDto().relatedTags().map { it.tag })
    }
}
