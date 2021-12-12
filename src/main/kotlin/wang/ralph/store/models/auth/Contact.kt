package wang.ralph.store.models.auth

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import wang.ralph.store.models.purchase.ReceiverContacts
import java.util.*

object Contacts : UUIDTable("contact") {
    val subject = reference("subject_id", Subjects)
    val name = varchar("name", 32)
    val mobile = varchar("mobile", 32)
    val address = varchar("address", 255)
    val postcode = varchar("postcode", 32)
}

// 联系方式
class Contact(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Contact>(Contacts)

    // 所属主体
    var subject by Subject referencedOn Contacts.subject

    // 称谓
    var name by ReceiverContacts.name

    // 手机号
    var mobile by ReceiverContacts.mobile

    // 地址
    var address by ReceiverContacts.address

    // 邮编
    var postcode by ReceiverContacts.postcode
}
