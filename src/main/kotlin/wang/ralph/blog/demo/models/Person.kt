package wang.ralph.blog.demo.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Persons : UUIDTable("person") {
}

class PersonDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PersonDAO>(Persons)

    fun toPerson(): Person = Person(id.value)
}

data class Person(
    override val id: UUID,
) : Subject(id)
