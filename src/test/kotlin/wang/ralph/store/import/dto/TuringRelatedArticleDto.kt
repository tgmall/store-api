package wang.ralph.store.import.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class TuringRelatedArticleDto {
    lateinit var createDate: Date
    var id: Int = 0
    var imageKey: String? = null

    @JsonProperty("isImageInBody")
    var isImageInBody: Boolean = false
    lateinit var subject: String
    lateinit var abstract: String
    var voteCount: Int = 0
    var favCount: Int = 0
    var commentCount: Int = 0
    var viewCount: Int = 0
    var publishDate: Date? = null
    var authorId: Int = 0
    lateinit var authorNickName: String
    var authorAvatarImageKey: String? = null
    var published: Boolean = false
    var locked: Boolean = false
    var forbidComment: Boolean = false
    var articlePersonalCategoryName: String? = null
    var articlePersonalCategoryId: Int = 0
    var tags: List<TuringBookTagDto> = emptyList()
}
