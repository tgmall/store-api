package wang.ralph.store.import.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TuringBookContributorDto {
    @JsonProperty("Auditor")
    var auditor: List<TuringPersonDto>? = null

    @JsonProperty("Author")
    var author: List<TuringPersonDto>? = null

    @JsonProperty("Editor")
    var editor: List<TuringPersonDto>? = null

    @JsonProperty("Translator")
    var translator: List<TuringPersonDto>? = null
}
