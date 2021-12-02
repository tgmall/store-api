package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.captcha.Captchas

fun initTestingCaptchaData() {
    SchemaUtils.drop(Captchas)
    SchemaUtils.create(Captchas)
}
