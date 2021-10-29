package wang.ralph.blog.demo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.Users
import wang.ralph.blog.demo.plugins.configureMonitoring
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSerialization

fun initDB() {
    val url = "jdbc:mysql://dev:dev@localhost:3306/auth?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
    }
}

fun main() {
    initDB()
    embeddedServer(Netty, port = 28081, watchPaths = listOf("classes")) {
        configureRouting()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
