package wang.ralph.blog.demo.routes

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import wang.ralph.blog.demo.services.UserData
import wang.ralph.blog.demo.services.UserService
import java.util.*

fun Route.userRouting() {
    route("/users") {
        val service = UserService()
        get {
            call.respond(service.query())
        }
        get("/{id}") {
            call.respond(service.get(call.id()))
        }

        get("/authenticate") {
            val username = call.parameters["username"] ?: throw BadRequestException("username is required")
            val password = call.parameters["password"] ?: throw BadRequestException("password is required")
            val user = service.authenticate(username, password)
            call.respond(user.id)
        }

        post("") {
            val userData = call.receive<UserData>()
            val user = service.create(userData)
            call.respondRedirect("/users/${user.id}")
        }
        put("/{id}") {
            val userData = call.receive<UserData>()
            service.update(call.id(), userData)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}

fun ApplicationCall.id(): UUID {
    return parameters["id"]?.let { UUID.fromString(it) } ?: throw BadRequestException("id is required")
}
