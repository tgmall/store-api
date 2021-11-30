package wang.ralph.store.application.account

import wang.ralph.store.models.account.Account
import wang.ralph.store.models.account.AccountType
import java.math.BigDecimal

fun Account.toDto(): AccountDto =
    AccountDto(type, points.map { it.toDto() }, moneys.map { it.toDto() }, moneyBalance, pointBalance)

data class AccountDto(
    val type: AccountType,
    val points: List<PointDto>,
    val moneys: List<MoneyDto>,
    val moneyBalance: BigDecimal,
    val pointBalance: BigDecimal,
)
