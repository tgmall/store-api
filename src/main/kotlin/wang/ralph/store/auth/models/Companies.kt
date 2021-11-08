package wang.ralph.store.auth.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Companies : UUIDTable("company") {
}

class Company(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Company>(Companies)

    fun toDto(): CompanyDto = CompanyDto(id.toString())
}

data class CompanyDto(
    override val id: String,
) : Subject(id)
