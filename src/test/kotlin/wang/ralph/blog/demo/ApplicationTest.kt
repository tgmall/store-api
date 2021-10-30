package wang.ralph.blog.demo

import io.ktor.http.*
import io.ktor.server.testing.*
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSerialization
import wang.ralph.blog.demo.utils.initTestDB
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testUsers() {
        initTestDB()
        withTestApplication({
            configureRouting()
            configureSerialization()
        }) {
            handleRequest(HttpMethod.Get, "/users").apply {
                assertEquals("[]", response.content)
                assertEquals(ContentType.Application.Json, response.contentType().withoutParameters())
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
