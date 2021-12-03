package wang.ralph.store.application.utils

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.captcha.CaptchaQuery
import wang.ralph.store.models.utils.MobileVerification

class MobileVerificationMutation {
    @GraphQLDescription("发送短信验证码")
    fun sendCodeViaSms(
        @GraphQLDescription("手机号")
        mobile: String,
        captchaId: String,
        captchaValue: String,
    ) = transaction {
        CaptchaQuery().verifyCaptcha(captchaId, captchaValue)
        // 作为测试系统，只使用固定的验证码，以方便测试
        val mobileVerification = MobileVerification.create(mobile, "123456")
        // TODO: 通过短信网关发送验证码
    }
}
