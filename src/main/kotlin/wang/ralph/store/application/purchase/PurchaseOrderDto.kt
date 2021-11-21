package wang.ralph.store.application.purchase

import wang.ralph.store.models.purchase.PurchaseOrder

fun PurchaseOrder.toDto(): PurchaseOrderDto =
    PurchaseOrderDto(id.toString(), userId.toString(), items.map { it.toDto() })

data class PurchaseOrderDto(val id: String, val userId: String?, val items: List<PurchaseOrderItemDto>)
