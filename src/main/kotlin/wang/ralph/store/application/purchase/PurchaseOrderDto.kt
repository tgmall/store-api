package wang.ralph.store.application.purchase

import wang.ralph.store.models.purchase.PurchaseOrder

fun PurchaseOrder.toDto(): PurchaseOrderDto = PurchaseOrderDto(
    id = id.toString(),
    userId = userId.toString(),
    items = items.map { it.toDto() },
    receiverContact = receiverContact.toDto(),
)

data class PurchaseOrderDto(
    val id: String,
    val userId: String?,
    val items: List<PurchaseOrderItemDto>,
    val receiverContact: ReceiverContactDto,
)
