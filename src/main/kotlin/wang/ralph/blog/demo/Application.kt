package wang.ralph.blog.demo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.application.UserInput
import wang.ralph.blog.demo.application.UserService
import wang.ralph.blog.demo.graphql.UserMutation
import wang.ralph.blog.demo.graphql.UserQuery
import wang.ralph.blog.demo.models.Companies
import wang.ralph.blog.demo.models.Persons
import wang.ralph.blog.demo.models.Users
import wang.ralph.blog.demo.plugins.configureMonitoring
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSerialization
import wang.ralph.graphql.configureGraphQL

fun initDB() {
    val url = "jdbc:mysql://dev:dev@localhost:3306/auth?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Persons)
        SchemaUtils.create(Companies)
    }
    initTestingData()
}

fun initTestingData() {
    val service = UserService()
    if (service.query().isEmpty()) {
        service.create(UserInput(
            username = "username",
            password = "password",
            nickName = "nickName",
            avatarUrl = "avatarUrl",
        ))
    }
}

fun main() {
    initDB()
    embeddedServer(Netty, port = 28081, watchPaths = listOf("classes")) {
        configureGraphQL(
            packageNames = listOf("wang.ralph"),
            queries = listOf(UserQuery()),
            mutations = listOf(UserMutation()),
        )
        configureRouting()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
