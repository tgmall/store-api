package wang.ralph.store

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.auth.graphql.UserMutation
import wang.ralph.store.auth.graphql.UserQuery
import wang.ralph.store.auth.models.*
import wang.ralph.store.cart.graphql.CartMutation
import wang.ralph.store.cart.graphql.CartQuery
import wang.ralph.store.cart.models.Cart
import wang.ralph.store.cart.models.Carts
import wang.ralph.store.plugins.configureMonitoring
import wang.ralph.store.plugins.configureRouting
import wang.ralph.store.plugins.configureSecurity
import wang.ralph.store.plugins.configureSerialization

fun initDB() {
    val url = "jdbc:mysql://dev:dev@localhost:3306/auth?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Persons)
        SchemaUtils.create(Companies)
        SchemaUtils.create(Carts)
    }
    initTestingData()
}

lateinit var user: User

fun initTestingData() = transaction {
    if (User.count() == 0L) {
        val subject = Person.new {
            name = "person"
        }
        user = User.new {
            subjectId = subject.id.value
            username = "username"
            encodedPassword = User.encodePassword("password")
            nickName = "nickName"
            avatarUrl = "avatarUrl"
        }
        Cart.new {
            subjectId = subject.id.value
        }
    }
}

fun main() {
    initDB()
    embeddedServer(Netty, port = 28081, watchPaths = listOf("classes")) {
        configureGraphQL(
            packageNames = listOf("wang.ralph"),
            queries = listOf(
                UserQuery(),
                CartQuery(),
            ),
            mutations = listOf(
                UserMutation(),
                CartMutation()
            ),
        )
        configureSecurity()
        configureRouting()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
