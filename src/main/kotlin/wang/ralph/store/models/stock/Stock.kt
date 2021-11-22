package wang.ralph.store.models.stock

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

object Stocks : UUIDTable("stock") {
    val skuId = uuid("sku_id")
    val skuAmount = decimal("sku_amount", 10, 3)
}

class Stock(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Stock>(Stocks) {
        fun increase(skuId: UUID, skuAmount: BigDecimal): Stock {
            val item = getOrCreate(skuId)
            item.skuAmount = (item.skuAmount + skuAmount).max(BigDecimal.ZERO)
            item.flush()
            return item;
        }

        fun decrease(skuId: UUID, amount: BigDecimal): Stock {
            return increase(skuId, -amount)
        }

        fun getOrCreate(skuId: UUID): Stock {
            return Stock.find { Stocks.skuId eq skuId }.firstOrNull() ?: Stock.new {
                this.skuId = skuId
                this.skuAmount = BigDecimal.ZERO
            }
        }
    }

    var skuId by Stocks.skuId
    var skuAmount by Stocks.skuAmount
}
