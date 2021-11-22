package wang.ralph.store.application.auth

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import wang.ralph.store.models.auth.User

private val defaultImageUri = "/default-avatar.svg"
fun User.toDto(): UserDto = UserDto(id.toString(), username, nickName, mobile, avatarUrl ?: defaultImageUri)

@GraphQLDescription("用户")
data class UserDto(
    val id: String,
    @GraphQLDescription("用户名")
    val username: String,
    @GraphQLDescription("昵称")
    val nickName: String = username,
    @GraphQLDescription("手机")
    val mobile: String,
    @GraphQLDescription("头像 url")
    val avatarUrl: String = "",
)
