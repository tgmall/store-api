package wang.ralph.store.auth.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Persons : UUIDTable("person") {
    val name = varchar("name", 32)
}

class Person(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Person>(Persons)

    var name: String by Persons.name

    fun toDto(): PersonDto = PersonDto(id.value.toString(), name)
}

data class PersonDto(
    override val id: String,
    val name: String,
) : Subject(id)
