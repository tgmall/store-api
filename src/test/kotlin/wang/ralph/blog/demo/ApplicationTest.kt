package wang.ralph.blog.demo

import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.Users
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSerialization
import kotlin.test.Test
import kotlin.test.assertEquals

fun initDB() {
    val url = "jdbc:h2:mem:;DATABASE_TO_UPPER=false;MODE=MYSQL"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
    }
}

class ApplicationTest {
    @Test
    fun testUsers() {
        initDB()
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
