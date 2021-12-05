package wang.ralph.store.application.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.configuration.JwtConfiguration
import wang.ralph.store.models.auth.User
import javax.security.auth.login.CredentialNotFoundException

fun Route.jwtRoute() {
    // 登录，获取 jwt token
    post("/jwt") {
        data class JwtRequest(val mobile: String, val password: String)
        data class JwtResponse(val userId: String, val jwt: String)

        val login: JwtRequest = call.receive()
        val user = transaction {
            User.authenticate(login.mobile, login.password) ?: throw CredentialNotFoundException()
        }
        val token = JWT.create()
            .withAudience(JwtConfiguration.audience)
            .withIssuer(JwtConfiguration.issuer)
            .withSubject(user.id.toString())
            .sign(Algorithm.HMAC256(JwtConfiguration.secret))
        call.respond(JwtResponse(user.id.toString(), token))
    }
}
