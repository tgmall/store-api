package wang.ralph.store.setup

import wang.ralph.store.models.stock.Stock
import java.math.BigDecimal

fun initStockData() {
    Stock.increase(skuA1.id.value, BigDecimal("100"))
    Stock.increase(skuA2.id.value, BigDecimal("200"))
    Stock.increase(skuB1.id.value, BigDecimal("2000"))
}
