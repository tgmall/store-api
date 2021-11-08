package wang.ralph.store.auth.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import java.util.*

@GraphQLDescription("更新用户信息")
data class UserUpdateInput(
    val id: UUID,
    @GraphQLDescription("姓名")
    val name: String? = null,
    @GraphQLDescription("昵称")
    val nickName: String? = null,
    @GraphQLDescription("头像 url")
    val avatarUrl: String? = null,
)
