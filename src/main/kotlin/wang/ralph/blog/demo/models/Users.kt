package wang.ralph.blog.demo.models

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Users : UUIDTable("user") {
    val subjectId = uuid("subject_id")
    val username = varchar("name", length = 64)
    val encodedPassword = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32)
    val avatarUrl = varchar("avatar)url", length = 128)
}

class UserDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDAO>(Users)

    var subjectId: UUID by Users.subjectId
    var username: String by Users.username
    var encodedPassword: String by Users.encodedPassword
    var nickName: String by Users.nickName
    var avatarUrl: String by Users.avatarUrl

    fun toUser(): User = User(id.value, subjectId, username, encodedPassword, nickName, avatarUrl)
}

data class User(
    val id: UUID,
    val subjectId: UUID,
    val username: String,
    var encodedPassword: String,
    val nickName: String,
    val avatarUrl: String,
)

internal fun encodePassword(password: String): String {
    return BCrypt.withDefaults().hashToString(6, password.toCharArray())
}

internal fun verifyPassword(password: String, encodedPassword: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified
}
