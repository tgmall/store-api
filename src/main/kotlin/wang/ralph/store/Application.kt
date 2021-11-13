package wang.ralph.store

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.application.CartMutation
import wang.ralph.store.application.CartQuery
import wang.ralph.store.application.UserMutation
import wang.ralph.store.application.UserQuery
import wang.ralph.store.plugins.configureMonitoring
import wang.ralph.store.plugins.configureRouting
import wang.ralph.store.plugins.configureSecurity
import wang.ralph.store.plugins.configureSerialization
import wang.ralph.store.setup.*

fun initDB() {
    val url = "jdbc:mysql://dev:dev@localhost:3306/store?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
}

fun main() {
    initDB()
    transaction {
        initTestingUserData()
        initTestingCartData()
        initTestingTagData()
        initTestingCommodityData()
        initTestingPriceData()
        initTestingCommodityCategoryData()
    }
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
