package wang.ralph.store.models.captcha

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.and
import java.awt.Font
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.security.SecureRandom
import java.util.*

object Captchas : UUIDTable("captcha") {
    val value = varchar("value", 32).clientDefault { randomText(4) }
}

class Captcha(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Captcha>(Captchas) {
        fun verify(id: UUID, value: String): Captcha? {
            val result = find { (Captchas.id eq id) and (Captchas.value eq value.uppercase()) }.firstOrNull()
            result?.delete()
            return result
        }
    }

    var value by Captchas.value

    fun generateImage(): BufferedImage = drawCaptcha(value)
}

val base24Charset = "ZAC2B3EF4GH5TK67P8RS9WXY";
internal fun randomText(size: Int, charset: String = base24Charset): String {
    return (1..size).map {
        val index = SecureRandom().nextInt(base24Charset.length)
        base24Charset[index]
    }.toCharArray().concatToString()
}

internal fun drawCaptcha(text: String): BufferedImage {
    val image = BufferedImage(140, 60, TYPE_INT_RGB)
    val canvas = image.graphics
    canvas.font = Font("Monospaced", Font.PLAIN, 50)
    canvas.drawString(text, 10, 50)
    return image;
}
