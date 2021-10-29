package wang.ralph.blog.demo.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import wang.ralph.blog.demo.models.DomainNotFoundException
import wang.ralph.blog.demo.routes.userRouting
import javax.security.auth.login.CredentialNotFoundException

fun Application.configureRouting() {
    install(StatusPages) {
        exception<CredentialNotFoundException> {
            call.respond(HttpStatusCode.Unauthorized, "无效的用户名或密码")
        }
        exception<DomainNotFoundException> {
            call.respond(HttpStatusCode.NotFound, it.message)
        }
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, it.message ?: "无效的参数")
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, "服务器内部错误：${it.stackTraceToString()}")
        }
    }
    install(Locations) {
    }

    routing {
        userRouting()
    }
}
