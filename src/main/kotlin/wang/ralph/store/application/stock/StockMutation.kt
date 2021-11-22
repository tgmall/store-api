package wang.ralph.store.application.stock

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.stock.Stock
import java.math.BigDecimal
import java.util.*

class StockMutation {
    fun stockIn(skuId: String, skuAmount: BigDecimal): StockDto = transaction {
        Stock.increase(UUID.fromString(skuId), skuAmount).toDto()
    }

    fun stockOut(skuId: String, skuAmount: BigDecimal): StockDto = transaction {
        Stock.decrease(UUID.fromString(skuId), skuAmount).toDto()
    }
}
