package wang.ralph.store.application.auth

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("创建/更新用户信息")
data class UserCreateInput(
    @GraphQLDescription("用户名")
    val username: String,
    @GraphQLDescription("密码")
    val password: String,
    @GraphQLDescription("昵称")
    val nickName: String = username,
    @GraphQLDescription("头像 url")
    val avatarUrl: String = "",
)
