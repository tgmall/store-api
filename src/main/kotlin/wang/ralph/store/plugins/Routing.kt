package wang.ralph.store.plugins

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import wang.ralph.graphql.graphql
import wang.ralph.graphql.graphqlPlayground
import wang.ralph.graphql.graphqlSchema
import wang.ralph.store.application.exceptions.DomainNotFoundException
import javax.security.auth.login.CredentialNotFoundException

fun Application.configureRouting() {
    install(StatusPages) {
        exception<CredentialNotFoundException> {
            call.respond(HttpStatusCode.Unauthorized, "无效的用户名或密码")
            throw it
        }
        exception<DomainNotFoundException> {
            call.respond(HttpStatusCode.NotFound, it.message)
            throw it
        }
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, it.message ?: "无效的参数")
            throw it
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, "服务器内部错误：${it.stackTraceToString()}")
            throw it
        }
    }
    routing {
        graphqlSchema()
        graphqlPlayground()
        authenticate("basic") {
            graphql()
        }
    }
}
