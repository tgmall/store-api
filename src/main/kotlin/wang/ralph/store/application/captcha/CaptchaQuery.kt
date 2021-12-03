package wang.ralph.store.application.captcha

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.captcha.Captcha
import java.util.*

class CaptchaQuery {
    fun verifyCaptcha(captchaId: String, captchaValue: String): CaptchaDto = transaction {
        Captcha.verify(UUID.fromString(captchaId), captchaValue)?.toDto() ?: throw IllegalArgumentException();
    }
}
