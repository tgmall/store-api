package wang.ralph.store.import.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TuringEBookChapterDto {
    var id: Int = 0

    @JsonProperty("isFree")
    var isFree: Boolean = false
    var subject: String = ""
    var lockType: Int = 0

    @JsonProperty("isCompleted")
    var isCompleted: Boolean = false
}
