package wang.ralph.store.models.auth

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

// 行为主体的类型
enum class SubjectType {
    // 个人
    Person,

    // 公司
    Company,
}

object Subjects : UUIDTable("subject") {
    val name = varchar("name", 255).nullable()
    val type = enumerationByName("type", 32, SubjectType::class)
    val description = text("description").nullable()
}

// 行为主体
class Subject(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Subject>(Subjects) {
        // 新建个人
        fun newPerson(name: String? = null, description: String? = null) = Subject.new {
            this.type = SubjectType.Person
            this.name = name
            this.description = description
        }

        // 新建公司
        fun newCompany(name: String? = null, description: String? = null) = Subject.new {
            this.type = SubjectType.Company
            this.name = name
            this.description = description
        }
    }

    // 名称
    var name by Subjects.name

    // 类型
    var type by Subjects.type

    // 简介
    var description by Subjects.description

    // 联系人
    val contacts by Contact referrersOn Contacts.subject
}


