package wang.ralph.store.auth.graphql

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.auth.models.User
import wang.ralph.store.auth.models.UserDto
import wang.ralph.store.plugins.subject
import javax.security.auth.login.CredentialNotFoundException

class UserQuery {
    fun user(dfe: DataFetchingEnvironment): UserDto = transaction {
        User[dfe.call.subject().userId].toDto()
    }

    fun authenticate(username: String, password: String): UserDto = transaction {
        User.authenticate(username, password)?.toDto() ?: throw CredentialNotFoundException()
    }
}
