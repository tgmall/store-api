package wang.ralph.store.application.auth

import wang.ralph.store.models.auth.Subject
import wang.ralph.store.models.auth.SubjectType

interface SubjectDto {
    val id: String
    val name: String
}

fun Subject.toDto(): SubjectDto = when (type) {
    SubjectType.Person -> PersonDto(id.toString(), name)
    SubjectType.Company -> CompanyDto(id.toString(), name)
}

class CompanyDto(override val id: String, override val name: String) : SubjectDto
class PersonDto(override val id: String, override val name: String) : SubjectDto
