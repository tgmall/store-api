package wang.ralph.store.import.dto

class TuringRelatedBookDto {
    var authors: List<TuringPersonDto> = emptyList()
    var translators: List<TuringBookDto> = emptyList()
    var bookStatus: String = ""
    var id: Int = 0
    var coverKey: String = ""
    var name: String = ""
    var isbn: String = ""
    var abstract: String = ""
    var bookEditionPrices: List<TuringBookEditionPriceDto> = emptyList()
    var authorNameString: String? = null
    var translatorNameString: String? = null
}
