package wang.ralph.store.models.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

object Users : UUIDTable("user") {
    val subjectId = uuid("subject_id")
    val username = varchar("name", length = 64)
    val mobile = varchar("mobile", 32)
    val encodedPassword = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32)
    val avatarUrl = varchar("avatar_url", length = 128).nullable()
}

@GraphQLDescription("用户")
class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users) {
        @GraphQLDescription("认证")
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

        @GraphQLDescription("创建/注册")
        fun create(
            username: String,
            password: String,
            nickName: String,
            mobile: String,
            avatarUrl: String? = null,
        ): User {
            val subject = Subject.newPerson(nickName)
            return User.new {
                this.subjectId = subject.id.value
                this.username = username
                this.mobile = mobile
                this.encodedPassword = encodePassword(password)
                this.nickName = nickName
                this.avatarUrl = avatarUrl
            }
        }
    }

    @GraphQLDescription("所属主体的 id")
    var subjectId: UUID by Users.subjectId

    @GraphQLDescription("用户名")
    var username: String by Users.username

    @GraphQLDescription("加密过的密码")
    var encodedPassword: String by Users.encodedPassword

    @GraphQLDescription("昵称")
    var nickName: String by Users.nickName

    @GraphQLDescription("手机")
    var mobile: String by Users.mobile

    @GraphQLDescription("头像 url")
    var avatarUrl: String? by Users.avatarUrl

    @GraphQLDescription("修改个人信息")
    fun update(nickName: String? = null, mobile: String? = null, avatarUrl: String? = null) {
        nickName?.let { this.nickName = it }
        mobile?.let { this.mobile = it }
        avatarUrl?.let { this.avatarUrl = it }
        flush()
    }

    @GraphQLDescription("修改密码")
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
