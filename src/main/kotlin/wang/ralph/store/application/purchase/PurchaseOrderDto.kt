package wang.ralph.store.application.purchase

import wang.ralph.store.models.purchase.PurchaseOrder
import java.math.BigDecimal

fun PurchaseOrder.toDto(): PurchaseOrderDto = PurchaseOrderDto(
    id = id.toString(),
    userId = userId.toString(),
    items = items.map { it.toDto() },
    receiverContact = receiverContact.toDto(),
    amount = amount
)

data class PurchaseOrderDto(
    val id: String,
    val userId: String?,
    val items: List<PurchaseOrderItemDto>,
    val receiverContact: ReceiverContactDto,
    val amount: BigDecimal,
)
