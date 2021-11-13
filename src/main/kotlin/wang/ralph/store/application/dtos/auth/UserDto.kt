package wang.ralph.store.application.dtos

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import wang.ralph.store.models.auth.User

private val defaultImageUri = "/default-avatar.svg"
fun User.toDto(): UserDto = UserDto(id.toString(), username, nickName, avatarUrl ?: defaultImageUri)

@GraphQLDescription("用户")
data class UserDto(
    val id: String,
    @GraphQLDescription("用户名")
    val username: String,
    @GraphQLDescription("昵称")
    val nickName: String = username,
    @GraphQLDescription("头像 url")
    val avatarUrl: String = "",
)
