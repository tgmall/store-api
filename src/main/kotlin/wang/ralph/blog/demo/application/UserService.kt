package wang.ralph.blog.demo.application

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.*
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

class UserService {
    fun query(): List<User> = transaction {
        UserDAO.all().map(UserDAO::toUser).toList()
    }

    fun get(id: UUID): User = transaction {
        UserDAO.findById(id)?.toUser() ?: throw UserNotFoundException(id)
    }

    fun authenticate(username: String, password: String): User = transaction {
        val user = UserDAO.find { Users.username eq username }.firstOrNull()?.toUser()
            ?: throw CredentialNotFoundException("Invalid username or password!")
        if (!verifyPassword(password, user.encodedPassword)) {
            throw CredentialNotFoundException()
        }
        user
    }

    fun create(userInput: UserInput): UUID = transaction {
        val subject = PersonDAO.new {}.toPerson()
        UserDAO.new {
            subjectId = subject.id
            username = userInput.username
            encodedPassword = encodePassword(userInput.password)
            nickName = userInput.nickName
            avatarUrl = userInput.avatarUrl
        }.toUser().id
    }

    fun update(id: UUID, userInput: UserInput) = transaction {
        val userDAO = UserDAO.findById(id) ?: throw UserNotFoundException(id)
        userDAO.username = userInput.username
        userDAO.encodedPassword = encodePassword(userInput.password)
        userDAO.nickName = userInput.nickName
        userDAO.avatarUrl = userInput.avatarUrl
        userDAO.flush()
    }
}
