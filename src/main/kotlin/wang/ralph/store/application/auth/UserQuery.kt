package wang.ralph.store.application.auth

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.auth.User
import wang.ralph.store.plugins.userId
import javax.security.auth.login.CredentialNotFoundException

class UserQuery {
    fun currentUser(dfe: DataFetchingEnvironment): UserDto = transaction {
        User[dfe.userId].toDto()
    }

    fun authenticate(mobile: String, password: String): UserDto = transaction {
        User.authenticate(mobile, password)?.toDto() ?: throw CredentialNotFoundException()
    }

    fun mobileExists(mobile: String): Boolean = transaction {
        User.mobileExists(mobile)
    }
}
