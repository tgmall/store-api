package wang.ralph.store.application.account

import wang.ralph.store.models.account.Money
import java.math.BigDecimal
import java.time.Instant

fun Money.toDto(): MoneyDto = MoneyDto(timeCreated, amount, reason)

class MoneyDto(
    val timeCreated: Instant,
    val amount: BigDecimal,
    val reason: String,
)
