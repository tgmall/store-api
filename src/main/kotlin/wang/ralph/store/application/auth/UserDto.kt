package wang.ralph.store.application.auth

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import wang.ralph.store.models.auth.User

private val defaultImageUri = "/default-avatar.svg"
fun User.toDto(): UserDto = UserDto(
    id = id.toString(),
    username = username,
    nickName = nickName,
    mobile = mobile,
    avatarUrl = avatarUrl ?: defaultImageUri,
    subject = subject.toDto(),
)

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
    @GraphQLDescription("主体")
    val subject: SubjectDto,
)
