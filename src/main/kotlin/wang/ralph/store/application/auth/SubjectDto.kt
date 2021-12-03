package wang.ralph.store.application.auth

import wang.ralph.store.models.auth.Subject
import wang.ralph.store.models.auth.SubjectType

fun Subject.toDto(): SubjectDto = SubjectDto(
    id = id.toString(),
    name = name,
    type = type,
    contact = contacts.map { it.toDto() }
)

class SubjectDto(
    val id: String,
    val name: String?,
    val type: SubjectType,
    val contact: List<ContactDto>,
)
