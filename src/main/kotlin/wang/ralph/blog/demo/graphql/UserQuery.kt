package wang.ralph.blog.demo.graphql

import wang.ralph.blog.demo.application.UserService
import wang.ralph.blog.demo.models.User
import java.util.*

class UserQuery(private val userService: UserService = UserService()) {
    fun user(id: UUID): User {
        return userService.get(id)
    }

    fun authenticate(username: String, password: String): User {
        return userService.authenticate(username, password)
    }
}
