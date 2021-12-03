package wang.ralph.store.models.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

object Users : UUIDTable("user") {
    val subject = reference("subject_id", Subjects)
    val username = varchar("name", length = 64).nullable().uniqueIndex()
    val mobile = varchar("mobile", 32).uniqueIndex()
    val encodedPassword = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32).default("")
    val avatarUrl = varchar("avatar_url", length = 128).nullable()
}

// 用户
class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users) {
        // 认证
        fun authenticate(mobile: String, password: String): User? {
            val user = find { Users.mobile eq mobile }.firstOrNull()
            return user?.let {
                if (verifyPassword(password, user.encodedPassword)) {
                    it
                } else {
                    null
                }
            }
        }

        // 注册
        fun register(
            // 手机号
            mobile: String,
            // 密码
            password: String,
        ): User {
            if (!User.find { Users.mobile eq mobile }.empty()) {
                throw UserMobileExistsException(mobile)
            }
            val subject = Subject.newPerson()
            return User.new {
                this.subject = subject
                this.mobile = mobile
                this.encodedPassword = encodePassword(password)
            }
        }
    }

    // 所属主体
    var subject by Subject referencedOn Users.subject

    // 用户名
    var username by Users.username

    // 加密过的密码
    var encodedPassword by Users.encodedPassword

    // 昵称
    var nickName by Users.nickName

    // 手机
    var mobile by Users.mobile

    // 头像 url
    var avatarUrl by Users.avatarUrl

    // 修改个人信息
    fun update(name: String? = null, nickName: String? = null, mobile: String? = null, avatarUrl: String? = null) {
        name?.let {
            this.subject.name = name
            this.subject.flush()
        }
        nickName?.let { this.nickName = it }
        mobile?.let { this.mobile = it }
        avatarUrl?.let { this.avatarUrl = it }
        flush()
    }

    // 修改密码
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
