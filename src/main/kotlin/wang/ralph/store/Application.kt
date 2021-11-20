package wang.ralph.store

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.application.*
import wang.ralph.store.plugins.configureMonitoring
import wang.ralph.store.plugins.configureRouting
import wang.ralph.store.plugins.configureSecurity
import wang.ralph.store.plugins.configureSerialization
import wang.ralph.store.setup.*

fun main() {
    setupDb()
    transaction {
        initAllTestingData()
    }
    createEmbeddedServer(28081)
        .start(wait = true)
}

fun createEmbeddedServer(port: Int): ApplicationEngine {
    return embeddedServer(Netty, port = port, watchPaths = listOf("classes")) {
        configureGraphQL(
            packageNames = listOf("wang.ralph"),
            queries = listOf(
                CartQuery(),
                CommodityQuery(),
                CommodityCategoryQuery(),
                UserQuery(),
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
    }
}

fun initAllTestingData() {
    initTestingUserData()
    initTestingCartData()
    initTestingTagData()
    initTestingCommodityData()
    initTestingPriceData()
    initTestingCommodityCategoryData()
    initTestingOrderData()
}
