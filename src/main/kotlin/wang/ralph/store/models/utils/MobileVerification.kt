package wang.ralph.store.models.utils

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.and
import java.util.*

object MobileVerifications : UUIDTable("mobile_verification") {
    val mobile = varchar("mobile", 32)
    val smsCode = varchar("sms_code", 32)
}

class MobileVerification(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MobileVerification>(MobileVerifications) {
        fun verify(mobile: String, smsCode: String): MobileVerification {
            return find { (MobileVerifications.mobile eq mobile) and (MobileVerifications.smsCode eq smsCode) }.firstOrNull()
                ?: throw IllegalArgumentException("Invalid sms code")
        }

        fun create(mobile: String, smsCode: String): MobileVerification {
            return MobileVerification.new {
                this.mobile = mobile
                this.smsCode = smsCode
            }
        }
    }

    var mobile by MobileVerifications.mobile
    var smsCode by MobileVerifications.smsCode
}
