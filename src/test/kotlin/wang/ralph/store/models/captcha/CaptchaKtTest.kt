package wang.ralph.store.models.captcha

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingCaptchaData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.*

internal class CaptchaKtTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingCaptchaData()
        }
    }

    @Test
    fun randomText() {
        val text = randomText(6)
        assertTrue { text.matches(Regex("[A-Z0-9]{6}")) }
    }

    @Test
    fun drawCaptcha() {
        val image = drawCaptcha("abcdef")
        val pixels = image.getRGB(30, 20, 2, 2, IntArray(2 * 2), 0, 0)
        val samplePixels = listOf(-16777216, -16777216, 0, 0)
        assertEquals(samplePixels, pixels.toList())
    }

    @Test
    fun verify(): Unit = transaction {
        val captcha = Captcha.new {
            value = "123"
        }
        captcha.flush()
        assertNotNull(Captcha.verify(captcha.id.value, "123"))
    }
}
