package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.stock.Stock
import wang.ralph.store.models.stock.Stocks
import java.math.BigDecimal

fun initTestingStockData() {
    SchemaUtils.drop(Stocks)
    SchemaUtils.create(Stocks)

    Stock.increase(skuA1.id.value, BigDecimal("100"))
    Stock.increase(skuA2.id.value, BigDecimal("200"))
    Stock.increase(skuB1.id.value, BigDecimal("2000"))
}
