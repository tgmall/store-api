package wang.ralph.blog.demo.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.User
import wang.ralph.blog.demo.models.UserNotFoundException
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

fun Route.userRouting() {
    route("/users") {
        get {
            val users = transaction { User.all().toList() }
            call.respond(users)
        }
        get("/{id}") {
            val user = User.findById(call.id()) ?: throw UserNotFoundException(call.id())
            call.respond(user)
        }

        get("/{id}/authenticate") {
            val id = call.id()
            val password = call.parameters["password"] ?: throw BadRequestException("password is required")
            val user = User.findById(id) ?: throw UserNotFoundException(id)
            val encodedPassword = user.password
            if (!verifyPassword(password, encodedPassword)) {
                throw CredentialNotFoundException()
            }
            call.respond(user.id)
        }

        data class UserData(
            val name: String,
            val password: String,
            val nickName: String,
            val avatarUrl: String,
        )
        post("") {
            val userData = call.receive<UserData>()
            val user = User.new {
                name = userData.name
                password = encodePassword(userData.password)
                nickName = userData.nickName
                avatarUrl = userData.avatarUrl
            }
            call.respondRedirect("/users/${user.id}")
        }
        put("/{id}") {
            val id = call.id()
            val userData = call.receive<UserData>()
            val user = User.findById(id) ?: throw UserNotFoundException(id)
            user.name = userData.name
            user.password = encodePassword(userData.password)
            user.nickName = userData.nickName
            user.avatarUrl = userData.avatarUrl
            user.flush()
            call.respond(HttpStatusCode.NoContent)
        }
    }
}

fun encodePassword(password: String): String {
    return BCrypt.withDefaults().hashToString(6, password.toCharArray())
}

fun verifyPassword(password: String, encodedPassword: String): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray()).verified
}

fun ApplicationCall.id(): UUID {
    return parameters["id"]?.let { UUID.fromString(it) } ?: throw BadRequestException("id is required")
}
