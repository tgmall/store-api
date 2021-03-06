package wang.ralph.store.models.price

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.recreateAllTables
import wang.ralph.store.setup.initCommodityData
import wang.ralph.store.setup.initPriceData
import wang.ralph.store.setup.setupTestingDb
import wang.ralph.store.setup.skuA1
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PriceTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            recreateAllTables()
            initCommodityData()
            initPriceData()
        }
    }

    @Test
    fun 开始时间是闭区间() = transaction {
        val price = Price.findPrice(skuA1.id.value, Instant.parse("2000-01-01T00:00:00Z"))
        assertEquals(BigDecimal("100.01"), price?.skuAmount)
    }

    @Test
    fun 结束时间是开区间() = transaction {
        val price1 = Price.findPrice(skuA1.id.value, Instant.parse("2000-01-01T00:00:04Z"))
        assertEquals(BigDecimal("100.01"), price1?.skuAmount)
        val price2 = Price.findPrice(skuA1.id.value, Instant.parse("2000-01-01T00:00:05Z"))
        assertEquals(BigDecimal("200.00"), price2?.skuAmount)
    }

    @Test
    fun `重叠时，以生效起始时间最晚的为准`() = transaction {
        val price = Price.findPrice(skuA1.id.value, Instant.parse("2000-01-01T00:00:10Z"))
        assertEquals(BigDecimal("200.00"), price?.skuAmount)
    }
}
