package wang.ralph.store.models.auth

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.setup.initTestingUserData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        assertEquals("user1", User.authenticate("user1", "123")?.username)
    }

    @Test
    fun create() = transaction {
        val user = createTestUser()
        assertNotNull(user)
        assertEquals("user1", user.username)
    }

    private fun createTestUser() = User.create(username = "user1", password = "123", nickName = "User1")

    @Test
    fun update() = transaction {
        val user = createTestUser()

        user.update(nickName = "nickName2")
        assertEquals("nickName2", User[user.id.value].nickName)
    }
}
