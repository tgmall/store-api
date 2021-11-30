package wang.ralph.store.models.account

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.math.BigDecimal
import java.util.*

// 账户类型
enum class AccountType {
    // 主账户
    Main,

    // 赠品账户
    Gift,
}

object Accounts : UUIDTable("account") {
    val subjectId = uuid("subject_id")
    val type = enumerationByName("type", 32, AccountType::class)
}

// 账户
class Account(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Account>(Accounts)

    // 所属主体
    var subjectId by Accounts.subjectId

    // 账户类型
    var type by Accounts.type

    // 积分记录
    val points by Point referrersOn Points.account

    // 资金记录
    val moneys by Money referrersOn Moneys.account

    val moneyBalance get() = moneys.sumOf { it.amount }
    val pointBalance get() = points.sumOf { it.amount }
    fun payOut(money: BigDecimal, point: BigDecimal, reason: String) {

    }

    fun charge(money: BigDecimal, reason: String) {

    }

    fun addPoint(point: BigDecimal, reason: String) {

    }
}
