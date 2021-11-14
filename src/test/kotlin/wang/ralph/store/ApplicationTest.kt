package wang.ralph.store

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.GraphQLRequest
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.application.UserQuery
import wang.ralph.store.models.auth.User
import wang.ralph.store.plugins.configureRouting
import wang.ralph.store.plugins.configureSerialization
import wang.ralph.store.plugins.toPrincipal
import wang.ralph.store.setup.initTestingUserData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    private val mapper = ObjectMapper()

    @InternalAPI
    @Test
    fun testUsers() {
        setupTestingDb()
        transaction {
            initTestingUserData()
        }
        withTestApplication({
            authentication {
                basic(name = "basic") {
                    validate { credentials ->
                        transaction {
                            User.authenticate(credentials.name, credentials.password)?.toPrincipal()
                        }
                    }
                }
            }

            configureGraphQL(listOf("wang.ralph"), queries = listOf(UserQuery()))
            configureRouting()
            configureSerialization()
        }) {
            val query: String = """
        query {
            user {
                username
            }
        }
    """.trimIndent()
            handleRequest(HttpMethod.Post, "/graphql", setup = {
                addHeader(HttpHeaders.Authorization, "Basic ${"username:password".encodeBase64()}")
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(GraphQLRequest(query = query.trimMargin())))
            }).apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json, response.contentType().withoutParameters())
                assertEquals("""{"data":{"user":{"username":"username"}}}""", response.content)
            }
        }
    }
}
