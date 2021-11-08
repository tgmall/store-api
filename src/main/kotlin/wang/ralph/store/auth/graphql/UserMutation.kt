package wang.ralph.store.auth.graphql

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.auth.models.*
import wang.ralph.store.common.ForbiddenException
import wang.ralph.store.plugins.subject

class UserMutation {
    fun createUser(input: UserCreateInput): UserDto = transaction {
        val user = User.create(input)
        user.toDto()
    }

    fun updateUser(dfe: DataFetchingEnvironment, input: UserUpdateInput): UserDto = transaction {
        val subject = dfe.call.subject()
        if (subject.userId != input.id) {
            throw ForbiddenException()
        }
        User.update(subject.subjectId, input)
        User[subject.subjectId].toDto()
    }

    fun changePassword(dfe: DataFetchingEnvironment, input: ChangePasswordInput) = transaction {
        val subject = dfe.call.subject()
        User.changePassword(subject.userId, input)
        User[subject.subjectId].toDto()
    }
}
