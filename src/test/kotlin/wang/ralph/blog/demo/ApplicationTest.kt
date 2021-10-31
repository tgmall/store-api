package wang.ralph.blog.demo

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.testing.*
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSerialization
import wang.ralph.blog.demo.utils.initTestDB
import wang.ralph.blog.demo.utils.testUserId
import wang.ralph.graphql.GraphQLRequest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    private val mapper = ObjectMapper()

    @Test
    fun testUsers() {
        initTestDB()
        withTestApplication({
            configureRouting()
            configureSerialization()
        }) {
            val query: String = """
        query user(id="$testUserId") {
            id
            name
        }
    """.trimIndent()
            handleRequest(HttpMethod.Post, "/graphql", setup = {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(GraphQLRequest(query = query.trimMargin())))
            }).apply {
                assertEquals("[]", response.content)
                assertEquals(ContentType.Application.Json, response.contentType().withoutParameters())
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
