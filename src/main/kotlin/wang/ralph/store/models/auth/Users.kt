package wang.ralph.store.models.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import wang.ralph.store.application.dtos.auth.ChangePasswordInput
import wang.ralph.store.application.dtos.auth.UserCreateInput
import wang.ralph.store.application.dtos.auth.UserUpdateInput
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

object Users : UUIDTable("user") {
    val subjectId = uuid("subject_id")
    val username = varchar("name", length = 64)
    val encodedPassword = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32)
    val avatarUrl = varchar("avatar_url", length = 128)
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

        fun create(userInput: UserCreateInput): User {
            val subject = Subject.newPerson(userInput.nickName)
            return User.new {
                subjectId = subject.id.value
                username = userInput.username
                encodedPassword = encodePassword(userInput.password)
                nickName = userInput.nickName
                avatarUrl = userInput.avatarUrl
            }
        }

        fun update(user: User, input: UserUpdateInput) {
            input.nickName?.let { user.nickName = it }
            input.avatarUrl?.let { user.avatarUrl = it }
            user.flush()
        }

        fun changePassword(user: User, input: ChangePasswordInput) {
            if (verifyPassword(input.oldPassword, user.encodedPassword)) {
                user.encodedPassword = encodePassword(input.newPassword)
                user.flush()
            } else {
                throw CredentialNotFoundException()
            }
        }

        fun encodePassword(password: String): String {
            return BCrypt.withDefaults().hashToString(6, password.toCharArray())
        }

        fun verifyPassword(password: String, encodedPassword: String): Boolean {
            return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified
        }
    }

    var subjectId: UUID by Users.subjectId
    var username: String by Users.username
    var encodedPassword: String by Users.encodedPassword
    var nickName: String by Users.nickName
    var avatarUrl: String by Users.avatarUrl

}

