package wang.ralph.store.plugins

import io.ktor.application.*
import io.ktor.auth.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.auth.models.User
import java.util.*
import javax.security.auth.login.CredentialNotFoundException

data class SubjectPrincipal(val subjectId: UUID, val userId: UUID) : Principal

fun Application.configureSecurity() {
    authentication {
        basic(name = "basic") {
            validate { credentials ->
                transaction {
                    User.authenticate(credentials.name, credentials.password)?.toPrincipal()
                }
            }
        }
    }
}

fun User.toPrincipal(): SubjectPrincipal = SubjectPrincipal(this.subjectId, this.id.value)

fun ApplicationCall.subject(): SubjectPrincipal {
    return this.principal() ?: throw CredentialNotFoundException()
}
