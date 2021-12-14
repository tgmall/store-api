package wang.ralph.store.import.dto

class TuringEBookDto {
    var id: Int = 0
    var listed: Boolean = false
    var readyForPush: Boolean = false
    var chapters: List<TuringEBookChapterDto> = emptyList()
}
