package wang.ralph.blog.demo.application

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("创建/更新用户信息")
data class UserInput(
    @GraphQLDescription("用户名")
    val username: String,
    @GraphQLDescription("密码")
    val password: String,
    @GraphQLDescription("昵称")
    val nickName: String = username,
    @GraphQLDescription("头像 url")
    val avatarUrl: String = "",
)
