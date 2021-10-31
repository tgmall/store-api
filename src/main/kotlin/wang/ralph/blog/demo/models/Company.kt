package wang.ralph.blog.demo.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Companies : UUIDTable("company") {
}

class CompanyDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CompanyDAO>(Companies)

    fun toCompany(): Company = Company(id.value)
}

data class Company(
    override val id: UUID,
) : Subject(id)
