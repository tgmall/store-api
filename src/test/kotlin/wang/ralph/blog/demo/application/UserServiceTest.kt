package wang.ralph.blog.demo.application

import org.junit.Test
import wang.ralph.blog.demo.utils.initTestDB
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class UserServiceTest {
    private val service = UserService()

    @BeforeTest()
    fun setup() {
        initTestDB()
    }

    @Test
    fun query() {
        assertEquals(emptyList(), service.query())
        createTestUser()
        assertEquals(listOf("user1"), service.query().map { it.username })
    }

    @Test
    fun get() {
        val id = createTestUser()
        assertEquals("user1", service.get(id).nickName)
    }

    @Test
    fun authenticate() {
        val id = createTestUser()
        assertEquals(id, service.authenticate("user1", "123").id)
    }

    @Test
    fun create() {
        val id = createTestUser()
        assertNotNull(id)
        assertEquals("user1", service.get(id).username)
    }

    private fun createTestUser() = service.create(UserInput(username = "user1", password = "123"))

    @Test
    fun update() {
        val id = createTestUser()
        service.update(id, UserInput(username = "user2", password = "456"))
        val user = service.get(id)
        assertEquals("user2", user.username)
        assertEquals("user2", user.nickName)
        assertEquals(id, service.authenticate("user2", "456").id)
    }
}
