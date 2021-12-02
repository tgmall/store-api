package wang.ralph.store.application.captcha

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.captcha.Captcha

class CaptchaMutation {
    fun createCaptcha(): CaptchaDto = transaction {
        val captcha = Captcha.new {}
        captcha.flush()
        captcha.toDto()
    }
}
