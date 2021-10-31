package wang.ralph.blog.demo.graphql

import wang.ralph.blog.demo.application.UserInput
import wang.ralph.blog.demo.application.UserService
import wang.ralph.blog.demo.models.User
import java.util.*

class UserMutation(private val userService: UserService = UserService()) {
    fun createUser(userInput: UserInput): User {
        val id = userService.create(userInput)
        return userService.get(id)
    }

    fun updateUser(id: UUID, userInput: UserInput): User {
        userService.update(id, userInput)
        return userService.get(id)
    }
}
