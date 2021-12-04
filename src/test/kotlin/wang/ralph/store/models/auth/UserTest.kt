package wang.ralph.store.models.auth

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingUserData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.*

internal class UserTest {
    @BeforeTest()
    fun setup() {
        setupTestingDb()
        transaction {
            initTestingUserData()
        }
    }

    @Test
    fun authenticate() = transaction {
        createTestUser()
        assertEquals("13333333334", User.authenticate("13333333334", "123")?.mobile)
    }

    @Test
    fun create() = transaction {
        val user = createTestUser()
        assertNotNull(user)
        assertEquals("13333333334", user.mobile)
    }

    private fun createTestUser() =
        User.register(password = "123", mobile = "13333333334")

    @Test
    fun update() = transaction {
        val user = createTestUser()

        user.update(nickName = "nickName2")
        assertEquals("nickName2", User[user.id.value].nickName)
    }

    @Test
    fun exists() = transaction {
        assertTrue(User.mobileExists("13333333333"))
    }
}
