package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.price.Price
import wang.ralph.store.models.price.Prices
import java.math.BigDecimal
import java.time.Instant

lateinit var priceA11: Price
lateinit var priceA12: Price

fun initTestingPriceData() {
    SchemaUtils.create(Prices)
    Prices.deleteAll()
    priceA11 = Price.new {
        skuId = skuA1.id.value
        amount = BigDecimal("100.01")
        validFrom = Instant.parse("2000-01-01T00:00:00Z")
        validTo = Instant.parse("2000-01-01T00:00:10Z")
    }
    priceA12 = Price.new {
        skuId = skuA1.id.value
        amount = BigDecimal("200.00")
        validFrom = Instant.parse("2000-01-01T00:00:05Z")
        validTo = Instant.parse("2000-01-01T00:00:20Z")
    }
}
