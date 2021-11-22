package wang.ralph.store.application.stock

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.stock.Stock
import java.util.*

class StockQuery {
    fun stock(skuId: String): StockDto = transaction {
        Stock.getOrCreate(UUID.fromString(skuId)).toDto()
    }
}
