package wang.ralph.blog.demo.services

import org.junit.Test
import wang.ralph.blog.demo.utils.initTestDB
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
        assertEquals(listOf("user1"), service.query().map { it.name })
    }

    @Test
    fun get() {
        val id = createTestUser().id.value
        assertEquals("user1", service.get(id).nickName)
    }

    @Test
    fun authenticate() {
        val id = createTestUser().id
        assertEquals(id, service.authenticate("user1", "123").id)
    }

    @Test
    fun create() {
        val user = createTestUser()
        assertNotNull(user.id.value)
        assertEquals("user1", user.nickName)
    }

    private fun createTestUser() = service.create(UserData(name = "user1", password = "123"))

    @Test
    fun update() {
        val id = createTestUser().id.value
        service.update(id, UserData(name = "user2", password = "456"))
        val user = service.get(id)
        assertEquals("user2", user.name)
        assertEquals("user2", user.nickName)
        assertEquals(id, service.authenticate("user2", "456").id.value)
    }

    @Test
    fun encodePassword() {
        assertTrue(service.verifyPassword("123", service.encodePassword("123")))
    }
}
