package wang.ralph.store.import.dto

class TuringBookCategoryDto {
    var id: Int = 0
    var name: String = ""
    var key: String? = null
    var children: List<TuringBookCategoryDto>? = null
}
