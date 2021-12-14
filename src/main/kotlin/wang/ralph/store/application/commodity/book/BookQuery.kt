package wang.ralph.store.application.book.book

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.commodity.book.BookDto
import wang.ralph.store.application.commodity.book.toDto
import wang.ralph.store.models.commodity.book.Book
import java.util.*

class BookQuery {
    fun book(id: String): BookDto = transaction {
        Book[UUID.fromString(id)].toDto()
    }
}
