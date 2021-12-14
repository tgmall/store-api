package wang.ralph.store.import.dto

import java.util.*

class TuringBookCommentReplyDto {
    var id: Int = 0
    var content: String = ""
    var commentId: Int = 0
    var replyDate: Date = Date()
    var userId: Int = 0
    var userNickName: String = ""
    var userAvatarImageKey: String? = null
    var canBeDeleted: Boolean = false
}
