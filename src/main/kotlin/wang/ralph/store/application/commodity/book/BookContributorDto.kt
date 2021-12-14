package wang.ralph.store.application.commodity.book

import wang.ralph.store.application.auth.SubjectDto
import wang.ralph.store.application.auth.toDto
import wang.ralph.store.models.commodity.book.BookContributor
import wang.ralph.store.models.commodity.book.BookContributorRole

fun BookContributor.toDto(): BookContributorDto = BookContributorDto(role, subject.toDto())

data class BookContributorDto(val role: BookContributorRole, val subject: SubjectDto)
