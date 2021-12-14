package wang.ralph.store.import.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.util.*

class TuringBookDto {
    var presaleDiscountEndTime: Date? = null
    var voteCount: Int = 0
    var favCount: Int = 0
    var commentCount: Int = 0
    var viewCount: Int = 0
    var authorNameString: String = ""
    var translatorNameString: String = ""
    var supportEpub: Boolean = false
    var epubKey: String? = null
    var key: String? = null
    var epubName: String? = null
    var epubFileSize: Int = 0
    var supportMobi: Boolean = false
    var mobiKey: String? = null
    var mobiName: String? = null
    var mobiFileSize: Int = 0
    var supportPdf: Boolean = false
    var pdfKey: String? = null
    var pdfName: String? = null
    var pdfFileSize: Int = 0
    var supportPushMobi: Boolean = false
    var supportPaper: Boolean = false
    var supportPOD: Boolean = false
    var publishDate: Date? = null

    @JsonProperty("isGift")
    var isGift: Boolean = false
    var categories: List<List<TuringBookCategoryDto>> = emptyList()
    var languageName: String? = null
    var contentTable: String? = null
    var deputyEditor: TuringPersonDto? = null
    var bookCollectionName: String? = null
    var userVote: Boolean = false
    var userFav: Boolean = false
    var contributor: TuringBookContributorDto =
        TuringBookContributorDto()
    var briefIntro: TuringBookBriefIntroDto =
        TuringBookBriefIntroDto()
    var originalBookInfo: TuringBookOriginalInfoDto? = null
    var externalSalesInfo: TuringBookExternalSalesInfoDto? = null
    var paperEditionInfo: TuringBookPaperEditionInfoDto? = null
    var sameCollectionBooks: List<TuringRelatedBookDto>? = null
    var relatedBooks: List<TuringRelatedBookDto> = emptyList()
    var relatedArticles: List<TuringRelatedArticleDto> = emptyList()
    var relatedLiveCourses: List<String> = emptyList()
    var hasGiftBook: Boolean = false
    var giftPoints: Int = 0
    var sampleFileKey: String? = null
    var extension: String? = null
    var linkText: String? = null
    var linkUrl: String? = null
    var tags: List<TuringBookTagDto> = emptyList()
    var tupubBookId: Int? = null
    var miniBookId: Int? = null
    var ebook: TuringEBookDto? = null
    var pressName: String = ""
    var encrypt: String = ""
    var audioContent: String? = null
    var resources: List<TuringBookResourceDto> = emptyList()
    var extendedResources: List<String> = emptyList()
    var firstSaleDiscount: Int? = null
    var hasBuyFirstSaleBook: Int = 0
    var hasBuy: Boolean = false
    var weight: BigDecimal = BigDecimal.ZERO
    var bookStatus: Int = 0
    var onSaleEdition: Int = 0
    var presale: Boolean = false
    var ebookWaitingForPay: Boolean = false
    var ownTheEbook: Boolean = false
    var hasBoughtEbook: Boolean = false
    var hasStock: Boolean = false
    var salesInfos: List<TuringBookSalesInfoDto> = emptyList()
    var videoKey: String? = null
    var canSalePaper: Boolean = false
    var canSaleEbook: Boolean = false
    var canSaleAudio: Boolean = false
    var canBeSaled: Boolean = false
    var id: Int = 0
    var coverKey: String? = null
    var name: String = ""
    var isbn: String? = null
    var abstract: String = ""
    var bookEditionPrices: List<TuringBookEditionPriceDto> = emptyList()
}
