package wang.ralph.store.application.commodity

import wang.ralph.store.models.commodity.Commodity
import wang.ralph.store.models.commodity.CommodityNotPricedException
import wang.ralph.store.models.commodity.CommodityType
import java.math.BigDecimal

fun Commodity.toDto(): CommodityDto = CommodityDto(id.toString(), type, name, description, skus.map { it.toDto() })

data class CommodityDto(
    val id: String,
    val type: CommodityType,
    val name: String,
    val description: String,
    val skus: List<SkuDto>,
) {
    fun prices(): List<BigDecimal> = skus.mapNotNull { it.price }
    fun maxPrice(): BigDecimal = prices().maxOrNull() ?: throw CommodityNotPricedException(id)
    fun minPrice(): BigDecimal = prices().minOrNull() ?: throw CommodityNotPricedException(id)
}
