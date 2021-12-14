package wang.ralph.store.application.commodity.book

import wang.ralph.store.models.commodity.book.Book
import java.math.BigDecimal
import java.time.Instant

fun Book.toDto(): BookDto = BookDto(
    press = press?.toDto(),
    publishDate = publishDate,
    weight = weight,
    isbn = isbn,
    abstract = abstract,
    highlight = highlight,
    contentTable = contentTable,
    coverUrl = coverUrl,
    language = language,
    contributors = contributors.map { it.toDto() },
)

class BookDto(
    val press: PressDto?,
    val publishDate: Instant?,
    val weight: BigDecimal?,
    val isbn: String?,
    val abstract: String?,
    val highlight: String?,
    val contentTable: String?,
    val coverUrl: String?,
    val language: String?,
    val contributors: List<BookContributorDto>,
)
