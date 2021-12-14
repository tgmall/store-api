package wang.ralph.store.models.commodity.book

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import wang.ralph.store.models.auth.Subject
import wang.ralph.store.models.auth.Subjects
import java.util.*

object BookContributors : UUIDTable("book_contributor") {
    val book = reference("book_id", Books)
    val role = enumerationByName("role", 32, BookContributorRole::class)
    val subject = reference("subject_id", Subjects)
}

class BookContributor(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BookContributor>(BookContributors)

    var book by Book referencedOn BookContributors.book
    var role by BookContributors.role
    var subject by Subject referencedOn BookContributors.subject
}
