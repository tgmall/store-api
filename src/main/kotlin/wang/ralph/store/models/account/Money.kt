package wang.ralph.store.models.account

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.util.*

object Moneys : UUIDTable("money") {
    val account = reference("account_id", Accounts)
    val payment = reference("payment", Payments)
    val timeCreated = timestamp("time_created").defaultExpression(CurrentTimestamp())
    val amount = decimal("amount", 10, 0)
    val reason = varchar("reason", 255)
}

// 充值记录
class Money(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Money>(Moneys)

    // 所属账户
    var account by Account referencedOn Moneys.account

    // 支付记录
    var payment by Moneys.payment

    // 创建时间
    var timeCreated by Moneys.timeCreated

    // 数量，正数表示充值，负数表示支出
    var amount by Moneys.amount

    // 充值/花费理由
    var reason by Moneys.reason
}
