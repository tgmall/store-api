package wang.ralph.store.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import graphql.schema.DataFetchingEnvironment
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.configuration.JwtConfiguration
import wang.ralph.store.models.auth.User
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

data class StorePrincipal(val mobile: String, val userId: UUID, val subjectId: UUID) : Principal

fun User.toStorePrincipal(): StorePrincipal = StorePrincipal(mobile, id.value, subject.id.value)

val DataFetchingEnvironment.principal: StorePrincipal
    get() {
        val call = getLocalContext<ApplicationCall>()
        return call.principal() ?: throw CredentialNotFoundException()
    }

val DataFetchingEnvironment.userId: UUID
    get() = principal.userId

val DataFetchingEnvironment.subjectId: UUID
    get() = principal.subjectId

fun Application.configureSecurity() {
    authentication {
        jwt("auth-jwt") {
            this.realm = JwtConfiguration.realm
            verifier(JWT
                .require(Algorithm.HMAC256(JwtConfiguration.secret))
                .withAudience(JwtConfiguration.audience)
                .withIssuer(JwtConfiguration.issuer)
                .build())
            validate { credential ->
                credential.subject?.let {
                    transaction {
                        val user = User.get(UUID.fromString(it))
                        user.toStorePrincipal()
                    }
                }
            }
        }
    }
}
