package wang.ralph.store.import.dto

import com.fasterxml.jackson.annotation.JsonProperty

class PaginationDto {
    var pageCount: Int = 0
    var totalItemCount: Int = 0
    var pageNumber: Int = 0
    var hasPreviousPage: Boolean = false
    var hasNextPage: Boolean = false

    @JsonProperty("isFirstPage")
    var isFirstPage: Boolean = false

    @JsonProperty("isLastPage")
    var isLastPage: Boolean = false
}
