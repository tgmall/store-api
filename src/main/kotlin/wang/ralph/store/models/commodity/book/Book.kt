package wang.ralph.store.models.commodity.book

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.util.*

object Books : UUIDTable("book") {
    val name = varchar("name", 255)
    val press = reference("press_id", Presses).nullable()
    val publishDate = timestamp("publish_date").nullable()
    val weight = decimal("weight", 10, 2).nullable()
    val isbn = varchar("isbn", 32).nullable()
    val abstract = text("abstract").nullable()
    val highlight = text("highlight").nullable()
    val contentTable = text("content_table").nullable()
    val coverUrl = varchar("cover_url", 512).nullable()
    val language = varchar("language", 32).nullable()
}

class Book(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Book>(Books)

    var name by Books.name
    var press by Press optionalReferencedOn Books.press
    var publishDate by Books.publishDate
    var weight by Books.weight
    var isbn by Books.isbn
    var abstract by Books.abstract
    var highlight by Books.highlight
    var contentTable by Books.contentTable
    var coverUrl by Books.coverUrl
    var language by Books.language
    val contributors by BookContributor referrersOn BookContributors.book

    val authors get() = contributors.filter { it.role == BookContributorRole.Author }
    val translators get() = contributors.filter { it.role == BookContributorRole.Translator }
    val editors get() = contributors.filter { it.role == BookContributorRole.Editor }
    val auditors get() = contributors.filter { it.role == BookContributorRole.Auditor }
}
