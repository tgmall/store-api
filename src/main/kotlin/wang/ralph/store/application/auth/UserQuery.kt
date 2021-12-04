package wang.ralph.store.application.auth

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.models.auth.User
import wang.ralph.store.plugins.subject
import javax.security.auth.login.CredentialNotFoundException

class UserQuery {
    fun currentUser(dfe: DataFetchingEnvironment): UserDto = transaction {
        User[dfe.call.subject().userId].toDto()
    }

    fun authenticate(mobile: String, password: String): UserDto = transaction {
        User.authenticate(mobile, password)?.toDto() ?: throw CredentialNotFoundException()
    }

    fun mobileExists(mobile: String): Boolean = transaction {
        User.mobileExists(mobile)
    }
}
