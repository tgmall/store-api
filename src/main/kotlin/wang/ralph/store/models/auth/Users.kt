package wang.ralph.store.models.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

object Users : UUIDTable("user") {
    val subjectId = uuid("subject_id")
    val username = varchar("name", length = 64)
    val encodedPassword = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32)
    val avatarUrl = varchar("avatar_url", length = 128).nullable()
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users) {
        fun authenticate(username: String, password: String): User? {
            val user = find { Users.username eq username }.firstOrNull()
            return user?.let {
                if (verifyPassword(password, user.encodedPassword)) {
                    it
                } else {
                    null
                }
            }
        }

        fun create(username: String, password: String, nickName: String, avatarUrl: String? = null): User {
            val subject = Subject.newPerson(nickName)
            return User.new {
                this.subjectId = subject.id.value
                this.username = username
                this.encodedPassword = encodePassword(password)
                this.nickName = nickName
                this.avatarUrl = avatarUrl
            }
        }
    }

    var subjectId: UUID by Users.subjectId
    var username: String by Users.username
    var encodedPassword: String by Users.encodedPassword
    var nickName: String by Users.nickName
    var avatarUrl: String? by Users.avatarUrl

    fun update(nickName: String? = null, avatarUrl: String? = null) {
        nickName?.let { this.nickName = it }
        avatarUrl?.let { this.avatarUrl = it }
        flush()
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        if (verifyPassword(oldPassword, encodedPassword)) {
            this.encodedPassword = encodePassword(newPassword)
            flush()
        } else {
            throw CredentialNotFoundException()
        }
    }
}

private fun encodePassword(password: String): String {
    return BCrypt.withDefaults().hashToString(6, password.toCharArray())
}

private fun verifyPassword(password: String, encodedPassword: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified
}
