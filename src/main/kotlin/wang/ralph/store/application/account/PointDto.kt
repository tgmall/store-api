package wang.ralph.store.application.account

import wang.ralph.store.models.account.Point
import java.math.BigDecimal
import java.time.Instant

fun Point.toDto(): PointDto = PointDto(timeCreated, amount, reason)

data class PointDto(val timeCreated: Instant, val amount: BigDecimal, val reason: String)
