package wang.ralph.store

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.application.auth.UserMutation
import wang.ralph.store.application.auth.UserQuery
import wang.ralph.store.application.cart.CartMutation
import wang.ralph.store.application.cart.CartQuery
import wang.ralph.store.application.commodity.CommodityCategoryQuery
import wang.ralph.store.application.commodity.CommodityQuery
import wang.ralph.store.application.purchase.PurchaseOrderQuery
import wang.ralph.store.application.shipping.ShipperQuery
import wang.ralph.store.application.shipping.ShippingOrderMutation
import wang.ralph.store.application.shipping.ShippingOrderQuery
import wang.ralph.store.application.stock.StockMutation
import wang.ralph.store.application.stock.StockQuery
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
                PurchaseOrderQuery(),
                ShipperQuery(),
                ShippingOrderQuery(),
                StockQuery(),
            ),
            mutations = listOf(
                UserMutation(),
                CartMutation(),
                ShippingOrderMutation(),
                StockMutation(),
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
    initTestingStockData()
    initTestingPriceData()
    initTestingCommodityCategoryData()
    initTestingShipperData()
    initTestingShippingOrderData()
    initTestingOrderData()
}
