package wang.ralph.store.auth.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

enum class SubjectType {
    Person,
    Company,
}

object Subjects : UUIDTable("subject") {
    val name = varchar("name", 255)
    val type = enumerationByName("type", 32, SubjectType::class)
}

class Subject(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Subject>(Subjects) {
        fun newPerson(name: String) = Subject.new {
            this.type = SubjectType.Person
            this.name = name
        }

        fun newCompany(name: String) = Subject.new {
            this.type = SubjectType.Company
            this.name = name
        }
    }

    var name: String by Subjects.name
    var type: SubjectType by Subjects.type

    fun toDto(): SubjectDto = when (type) {
        SubjectType.Person -> PersonDto(id.value, name)
        SubjectType.Company -> CompanyDto(id.value, name)
    }
}


interface SubjectDto {
    val id: UUID
    val name: String
}

class PersonDto(override val id: UUID, override val name: String) : SubjectDto
class CompanyDto(override val id: UUID, override val name: String) : SubjectDto
