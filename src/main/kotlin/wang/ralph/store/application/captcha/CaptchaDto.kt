package wang.ralph.store.application.captcha

import wang.ralph.store.models.captcha.Captcha
import java.awt.image.BufferedImage

fun Captcha.toDto(): CaptchaDto = CaptchaDto(id.toString(), generateImage())
data class CaptchaDto(val id: String, val imageUrl: BufferedImage)
