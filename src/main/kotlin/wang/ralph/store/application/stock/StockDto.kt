package wang.ralph.store.application.stock

import wang.ralph.store.models.stock.Stock
import java.math.BigDecimal

fun Stock.toDto(): StockDto = StockDto(id.toString(), skuId.toString(), skuAmount)

data class StockDto(val id: String, val skuId: String, val skuAmount: BigDecimal)
