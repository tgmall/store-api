package wang.ralph.store.models.account

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.util.*

object Points : UUIDTable("point") {
    val account = reference("account_id", Accounts)
    val timeCreated = timestamp("time_created")
    val amount = decimal("amount", 10, 0)
    val reason = varchar("reason", 255)
}

// 积分记录
class Point(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Point>(Points)

    // 所属账户
    var account by Account referencedOn Points.account

    // 创建时间
    var timeCreated by Points.timeCreated

    // 数量，正数为收入，负数为支出
    var amount by Points.amount

    // 理由
    var reason by Points.reason
}
