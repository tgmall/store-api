package wang.ralph.store.import.dto

class TuringBookCommentDto {
    var id: Int = 0
    var userId: Int = 0
    var userNickName: String = ""
    var userAvatarImageKey: String? = null
    var content: String = ""
    var commentDate: String = ""
    var canBeDeleted: Boolean = false
    var voteCount: Int = 0
    var voted: Boolean = false
    var objectId: Int = 0
    var objectType: Int = 0
    var objectName: String = ""
    var replies: List<TuringBookCommentReplyDto> = emptyList()
}
