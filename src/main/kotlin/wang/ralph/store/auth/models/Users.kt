package wang.ralph.store.auth.models

import at.favre.lib.crypto.bcrypt.BCrypt
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import wang.ralph.store.common.ForbiddenException
import java.util.*

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

        fun update(id: UUID, input: UserUpdateInput) {
            val user = findById(id) ?: throw UserNotFoundException(id)
            input.nickName?.let { user.nickName = it }
            input.avatarUrl?.let { user.avatarUrl = it }
            user.flush()
        }

        fun changePassword(id: UUID, input: ChangePasswordInput) {
            val user = findById(id) ?: throw UserNotFoundException(id)
            if (verifyPassword(input.oldPassword, user.encodedPassword)) {
                user.encodedPassword = encodePassword(input.newPassword)
                user.flush()
            } else {
                throw ForbiddenException()
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

    fun toDto(): UserDto = UserDto(id.toString(), username, nickName, avatarUrl)
}

@GraphQLDescription("用户")
data class UserDto(
    val id: String,
    @GraphQLDescription("用户名")
    val username: String,
    @GraphQLDescription("昵称")
    val nickName: String = username,
    @GraphQLDescription("头像 url")
    val avatarUrl: String = "",
)
