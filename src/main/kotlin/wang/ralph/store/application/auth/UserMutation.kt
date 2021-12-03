package wang.ralph.store.application.auth

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import graphql.schema.DataFetchingEnvironment
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.call
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.auth.UserNotFoundException
import wang.ralph.store.models.utils.MobileVerification
import wang.ralph.store.plugins.subject

class UserMutation {
    @GraphQLDescription("注册新用户")
    fun register(
        @GraphQLDescription("手机号")
        mobile: String,
        @GraphQLDescription("手机验证码")
        smsCode: String,
        @GraphQLDescription("密码")
        password: String,
    ): UserDto = transaction {
        MobileVerification.verify(mobile, smsCode)
        val user = User.register(
            password = password,
            mobile = mobile,
        )
        user.toDto()
    }

    @GraphQLDescription("用户修改自身的信息")
    fun updateMyProfile(
        dfe: DataFetchingEnvironment,
        @GraphQLDescription("姓名")
        name: String? = null,
        @GraphQLDescription("昵称")
        nickName: String? = null,
        @GraphQLDescription("头像 url")
        avatarUrl: String? = null,
    ): UserDto = transaction {
        val subject = dfe.call.subject()
        val user = User.findById(subject.userId) ?: throw UserNotFoundException(subject.userId)
        user.update(name = name, nickName = nickName, avatarUrl = avatarUrl)
        User[subject.userId].toDto()
    }

    @GraphQLDescription("修改自身的密码")
    fun changeMyPassword(
        dfe: DataFetchingEnvironment,
        @GraphQLDescription("旧密码")
        oldPassword: String,
        @GraphQLDescription("新密码")
        newPassword: String,
    ) = transaction {
        val subject = dfe.call.subject()
        val user = User.findById(subject.userId) ?: throw UserNotFoundException(subject.userId)
        user.changePassword(oldPassword, newPassword)
        User[subject.userId].toDto()
    }
}
