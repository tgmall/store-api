package wang.ralph.store.import

import com.fasterxml.jackson.databind.ObjectMapper
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.import.dto.TuringBookCategoryDto
import wang.ralph.store.import.dto.TuringBookDto
import wang.ralph.store.import.dto.TuringBookEditionPriceDto
import wang.ralph.store.models.auth.Subject
import wang.ralph.store.models.commodity.*
import wang.ralph.store.models.commodity.book.Book
import wang.ralph.store.models.commodity.book.BookContributor
import wang.ralph.store.models.commodity.book.BookContributorRole
import wang.ralph.store.models.commodity.book.Press
import wang.ralph.store.models.portal.CommodityCategory
import wang.ralph.store.models.portal.CommodityCategoryTag
import wang.ralph.store.models.tag.Tag
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.isRegularFile
import kotlin.io.path.pathString
import kotlin.io.path.readText
import kotlin.streams.toList

fun importTuringData() {
    val bookCategory = transaction {
        CommodityCategory.new {
            this.name = "书籍"
        }
    }

    val categories = readTuringBookCategories()
    transaction {
        importTuringBookCategories(categories, bookCategory)
    }
    importTuringBooks()
}

fun readTuringBookCategories(): List<TuringBookCategoryDto> {
    val text = File("../data/turing/home/all.json").readText(Charsets.UTF_8)
    val mapper = ObjectMapper()
    val elementType = mapper.typeFactory.constructParametricType(List::class.java, TuringBookCategoryDto::class.java)

    return mapper.readValue(text, elementType)
}

fun importTuringBookCategories(categories: List<TuringBookCategoryDto>, parent: CommodityCategory) {
    categories.forEach { category ->
        val result = CommodityCategory.new {
            this.name = category.name.trim()
            this.parent = parent
        }
        CommodityCategoryTag.new {
            this.category = result
            this.tag = category.name.trim()
        }
        category.children?.let {
            importTuringBookCategories(it, result)
        }
    }
}

fun importTuringBooks() {
    val files = Files.list(Path.of("../data/turing/books"))
    val mapper = ObjectMapper()
    val books = files.filter { it.isRegularFile() }.map {
        println("importing: ${it.pathString}")
        val text = it.readText(Charsets.UTF_8)
        mapper.readValue(text, TuringBookDto::class.java)
    }.toList()

    val subjects = books.map {
        listOfNotNull(it.contributor.editor,
            it.contributor.auditor,
            it.contributor.author,
            it.contributor.translator,
            it.deputyEditor?.let { listOf(it) }).flatten()
    }.toList().flatten().distinctBy { it.id }

    val subjectMap = subjects.associate {
        it.id to transaction { Subject.newPerson(it.name.trim()) }
    }

    val tags = books.map { it.tags }.toList().flatten().distinctBy { it.id }

    val tagMaps = tags.associate {
        it.id to transaction { Tag.newCommodityTag(it.name.trim()) }
    }

    books.map { it.pressName.trim() }.distinct().forEach {
        transaction {
            Press.new {
                this.name = it
            }.flush()
        }
    }

    books.forEach { bookDto ->
        transaction {
            // 创建商品
            val commodity = Commodity.new {
                this.name = bookDto.name.trim()
                this.type = CommodityType.Book
                this.description = bookDto.abstract.trim()
            }
            val book = Book.new(commodity.id.value) {
                this.name = bookDto.name.trim()
                this.press = Press.findByName(bookDto.pressName.trim())
                this.publishDate = bookDto.publishDate?.toInstant()
                this.weight = bookDto.weight
                this.isbn = bookDto.isbn?.trim()
                this.abstract = bookDto.briefIntro.abstract.trim()
                this.highlight = bookDto.briefIntro.highlight?.trim()
                this.contentTable = bookDto.contentTable?.trim()
                this.coverUrl = "https://file.ituring.com.cn/LargeCover/${bookDto.coverKey}"
                this.language = bookDto.languageName?.trim()
            }
            bookDto.contributor.author?.forEach {
                BookContributor.new {
                    this.book = book
                    this.role = BookContributorRole.Author
                    this.subject = subjectMap[it.id]!!
                }
            }
            bookDto.contributor.translator?.forEach {
                BookContributor.new {
                    this.book = book
                    this.role = BookContributorRole.Translator
                    this.subject = subjectMap[it.id]!!
                }
            }
            bookDto.contributor.auditor?.forEach {
                BookContributor.new {
                    this.book = book
                    this.role = BookContributorRole.Auditor
                    this.subject = subjectMap[it.id]!!
                }
            }
            bookDto.contributor.editor?.forEach {
                BookContributor.new {
                    this.book = book
                    this.role = BookContributorRole.Editor
                    this.subject = subjectMap[it.id]!!
                }
            }
            bookDto.deputyEditor?.let {
                BookContributor.new {
                    this.book = book
                    this.role = BookContributorRole.Editor
                    this.subject = subjectMap[it.id]!!
                }
            }
            // 为商品分类关联标签
            bookDto.categories.flatten().distinctBy { it.name.trim() }.forEach {
                CommodityTag.new {
                    this.commodity = commodity
                    this.tag = it.name.trim()
                }
            }

            bookDto.tags.forEach {
                CommodityTag.new {
                    this.commodity = commodity
                    this.tag = it.name.trim()
                }
            }
            bookDto.bookEditionPrices
                .ifEmpty { listOf(TuringBookEditionPriceDto(key = "Paper", name = "0.00")) }
                .forEach { editionDto ->
                    val keys = editionDto.key?.split(",")?.map { it.trim() } ?: listOf("Paper")
                    keys.forEach { key ->
                        val sku = Sku.new {
                            this.commodity = commodity
                            this.code = key
                            this.name = when (key) {
                                "Paper" -> "纸质书"
                                "Ebook" -> "电子书"
                                else -> throw IllegalArgumentException(key)
                            }
                            this.price = BigDecimal(editionDto.name)
                        }
                        SkuImage.new {
                            this.sku = sku
                            this.smallImageUrl = "https://file.ituring.com.cn/SmallCover/${bookDto.coverKey}"
                            this.largeImageUrl = "https://file.ituring.com.cn/LargeCover/${bookDto.coverKey}"
                        }
                    }
                }
        }
    }
}
