package wang.ralph.store.application.account

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import wang.ralph.store.models.account.Payment
import wang.ralph.store.models.account.PaymentStatus
import java.math.BigDecimal
import java.time.Instant

fun Payment.toDto(): PaymentDto = PaymentDto(
    userId = userId.toString(),
    createdAt = createdAt,
    gateway = gateway.toString(),
    voucherNum = voucherNum,
    reason = reason,
    amount = amount,
    status = status,
)

@GraphQLDescription("支付单")
data class PaymentDto(
    @GraphQLDescription("支付用户")
    val userId: String,

    @GraphQLDescription("创建时间")
    val createdAt: Instant,

    @GraphQLDescription("支付网关")
    val gateway: String,

    @GraphQLDescription("凭证号")
    val voucherNum: String?,

    @GraphQLDescription("金额")
    val amount: BigDecimal,

    @GraphQLDescription("支付理由")
    val reason: String,

    @GraphQLDescription("状态")
    val status: PaymentStatus,
)
