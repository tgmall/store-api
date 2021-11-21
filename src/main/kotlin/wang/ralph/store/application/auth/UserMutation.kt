package wang.ralph.store.application.auth

import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.application.dtos.UserDto
import wang.ralph.store.application.dtos.toDto
import wang.ralph.store.application.exceptions.ForbiddenException
import wang.ralph.store.application.exceptions.UserNotFoundException
import wang.ralph.store.models.auth.User
import wang.ralph.store.plugins.subject

class UserMutation {
    fun createUser(input: UserCreateInput): UserDto = transaction {
        val user = User.create(
            username = input.username,
            password = input.password,
            nickName = input.nickName,
            avatarUrl = input.avatarUrl,
        )
        user.toDto()
    }

    fun updateUser(dfe: DataFetchingEnvironment, input: UserUpdateInput): UserDto = transaction {
        val subject = dfe.call.subject()
        if (subject.userId != input.id) {
            throw ForbiddenException()
        }
        val user = User.findById(subject.userId) ?: throw UserNotFoundException(subject.userId)
        user.update(nickName = input.nickName, avatarUrl = input.avatarUrl)
        User[subject.userId].toDto()
    }

    fun changePassword(dfe: DataFetchingEnvironment, input: ChangePasswordInput) = transaction {
        val subject = dfe.call.subject()
        val user = User.findById(subject.userId) ?: throw UserNotFoundException(subject.userId)
        user.changePassword(input.oldPassword, input.newPassword)
        User[subject.userId].toDto()
    }
}
