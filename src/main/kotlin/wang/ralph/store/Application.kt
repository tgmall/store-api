package wang.ralph.store

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.configureGraphQL
import wang.ralph.store.application.account.PaymentGatewayQuery
import wang.ralph.store.application.account.PaymentMutation
import wang.ralph.store.application.auth.UserMutation
import wang.ralph.store.application.auth.UserQuery
import wang.ralph.store.application.captcha.CaptchaMutation
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
import wang.ralph.store.application.utils.MobileVerificationMutation
import wang.ralph.store.models.account.PaymentGateways
import wang.ralph.store.models.account.Payments
import wang.ralph.store.models.auth.Contacts
import wang.ralph.store.models.auth.Subjects
import wang.ralph.store.models.auth.Users
import wang.ralph.store.models.captcha.Captchas
import wang.ralph.store.models.cart.CartItems
import wang.ralph.store.models.cart.Carts
import wang.ralph.store.models.commodity.Commodities
import wang.ralph.store.models.commodity.CommodityTags
import wang.ralph.store.models.commodity.SkuImages
import wang.ralph.store.models.commodity.Skus
import wang.ralph.store.models.portal.CommodityCategories
import wang.ralph.store.models.portal.CommodityCategoryTags
import wang.ralph.store.models.price.Prices
import wang.ralph.store.models.purchase.PurchaseOrderItems
import wang.ralph.store.models.purchase.PurchaseOrders
import wang.ralph.store.models.purchase.ReceiverContacts
import wang.ralph.store.models.purchase.SkuSnapshots
import wang.ralph.store.models.shipping.Shippers
import wang.ralph.store.models.shipping.ShippingOrderItems
import wang.ralph.store.models.shipping.ShippingOrders
import wang.ralph.store.models.stock.Stocks
import wang.ralph.store.models.tag.Tags
import wang.ralph.store.models.utils.MobileVerifications
import wang.ralph.store.plugins.configureMonitoring
import wang.ralph.store.plugins.configureRouting
import wang.ralph.store.plugins.configureSecurity
import wang.ralph.store.plugins.configureSerialization
import wang.ralph.store.setup.setupDb

fun main() {
    setupDb()
    transaction {
        updateAllTables()
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
                PaymentGatewayQuery(),

                // CaptchaQuery(), // 仅供内部使用，不要对外暴露
            ),
            mutations = listOf(
                UserMutation(),
                CartMutation(),
                ShippingOrderMutation(),
                StockMutation(),
                PaymentMutation(),
                CaptchaMutation(),
                MobileVerificationMutation(),
            ),
        )
        configureSecurity()
        configureRouting()
        configureMonitoring()
        configureSerialization()
    }
}

val allTables = SchemaUtils.sortTablesByReferences(listOf(
    Captchas,
    Carts,
    CartItems,
    CommodityCategories,
    CommodityCategoryTags,
    Commodities,
    Skus,
    SkuImages,
    CommodityTags,
    MobileVerifications,
    PaymentGateways,
    Payments,
    Prices,
    PurchaseOrders,
    PurchaseOrderItems,
    SkuSnapshots,
    ReceiverContacts,
    Shippers,
    ShippingOrders,
    ShippingOrderItems,
    Stocks,
    Tags,
    Subjects,
    Users,
    Contacts,
)).toTypedArray()

fun updateAllTables() {
    SchemaUtils.createMissingTablesAndColumns(*allTables)
}

fun recreateAllTables() {
    SchemaUtils.drop(*allTables.reversed().toTypedArray())
    SchemaUtils.create(*allTables)
}
