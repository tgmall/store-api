package wang.ralph.blog.demo.services

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.User
import wang.ralph.blog.demo.models.UserNotFoundException
import wang.ralph.blog.demo.models.Users
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

class UserService {
    fun query(): List<User> = transaction {
        User.all().toList()
    }

    fun get(id: UUID): User = transaction {
        User.findById(id) ?: throw UserNotFoundException(id)
    }

    fun authenticate(username: String, password: String): User = transaction {
        val user = User.find { Users.name eq username }.firstOrNull()
            ?: throw CredentialNotFoundException("Invalid username or password!")
        val encodedPassword = user.password
        if (!verifyPassword(password, encodedPassword)) {
            throw CredentialNotFoundException()
        }
        user
    }

    fun create(userData: UserData): User = transaction {
        User.new {
            name = userData.name
            password = encodePassword(userData.password)
            nickName = userData.nickName
            avatarUrl = userData.avatarUrl
        }
    }

    fun update(id: UUID, userData: UserData) = transaction {
        val user = User.findById(id) ?: throw UserNotFoundException(id)
        user.name = userData.name
        user.password = encodePassword(userData.password)
        user.nickName = userData.nickName
        user.avatarUrl = userData.avatarUrl
        user.flush()
    }


    internal fun encodePassword(password: String): String {
        return BCrypt.withDefaults().hashToString(6, password.toCharArray())
    }

    internal fun verifyPassword(password: String, encodedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified
    }
}
