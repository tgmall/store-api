package wang.ralph.store.auth.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("修改密码")
data class ChangePasswordInput(
    @GraphQLDescription("旧密码")
    val oldPassword: String,
    @GraphQLDescription("新密码")
    val newPassword: String,
)
