package wang.ralph.store.import.dto

class TuringBookCommentListDto {
    var tab: String = ""
    var sort: String = ""
    var objectId: Int = 0
    var allowComment: Boolean = false
    var showComment: Boolean = false
    var commentCount: Int = 0
    var pagination: PaginationDto = PaginationDto()
    var comments: List<TuringBookCommentDto> = emptyList()
}
