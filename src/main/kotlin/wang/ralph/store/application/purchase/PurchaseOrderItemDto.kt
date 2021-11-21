package wang.ralph.store.application.purchase

import wang.ralph.store.models.purchase.PurchaseOrderItem
import java.math.BigDecimal

fun PurchaseOrderItem.toDto(): PurchaseOrderItemDto =
    PurchaseOrderItemDto(id.toString(), skuId.toString(), actualPrice, actualAmount, skuSnapshot.toDto())

data class PurchaseOrderItemDto(
    val id: String,
    val skuId: String,
    val actualPrice: BigDecimal,
    val actualAmount: BigDecimal,
    val skuSnapshot: SkuSnapshotDto,
)
