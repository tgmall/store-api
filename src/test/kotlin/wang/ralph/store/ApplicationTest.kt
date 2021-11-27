package wang.ralph.store

import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.application.auth.UserDto
import wang.ralph.store.application.cart.CartDto
import wang.ralph.store.graphql.GqlUtils
import wang.ralph.store.models.shipping.ShippingOrderStatusEnum
import wang.ralph.store.setup.setupTestingDb
import java.math.BigDecimal
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@InternalAPI
class ApplicationTest {
    companion object {
        private const val port = 55553
        private var inited = false
    }

    private val gql = GqlUtils(port)

    @BeforeTest
    fun setup() {
        if (!inited) {
            setupTestingDb()
            transaction {
                initAllTestingData()
            }
            createEmbeddedServer(port)
                .start(wait = false)
            inited = true
        }
    }

    @Test
    fun currentUser() = runBlocking {
        gql.loginAs("username", "password")
        val user: UserDto = gql.getCurrentUser()
        assertEquals("username", user.username)
    }

    @Test
    fun commodityMaintainE2e() = runBlocking {
        // 登录
        // 添加商品名录
        // 添加二级商品名录
        // 添加商品
        // 将多个商品组合成一个新商品
        // 设置商品标签
        // 添加 SKU
        // SKU 定价
        // 设置初始 SKU 数量
        // 商品上架
    }

    @Test
    fun consumerE2e() = runBlocking {
        // 注册新用户
        val user = gql.createUser("wzc", "wzc", "13333333333")
        assertEquals("wzc", user.username)
        gql.loginAs("wzc", "wzc")
        // 查看商品名录
        val categories = gql.listCommodityCategories()
        assertEquals(listOf("root"), categories.map { it.name })
        // 查看商品列表
        val commodities = gql.listCommodities(categories.first().relatedTags.map { it.tag })
        assertEquals(listOf("commodityA", "commodityB"), commodities.map { it.name })
        // 查看商品详情
        val commodity = gql.getCommodity(commodities.first().id)
        assertEquals(BigDecimal("100.01"), commodity.minPrice())
        assertEquals(BigDecimal("200.00"), commodity.maxPrice())
        assertEquals(listOf("blueA", "greenA"), commodity.skus.map { it.name })
        // 显示库存
        val stock = gql.getStock(commodity.skus.first().id)
        assertEquals(BigDecimal("100.000"), stock.skuAmount)
        // 加入购物车
        lateinit var cart: CartDto
        cart = gql.addCartItem(commodity.skus.first().id)
        assertEquals(listOf(BigDecimal("1.00")), cart.items.map { it.skuAmount })
        assertEquals(BigDecimal("100.0100"), cart.total())
        // 重复添加
        cart = gql.addCartItem(commodity.skus.first().id)
        assertEquals(listOf(BigDecimal("2.00")), cart.items.map { it.skuAmount })
        assertEquals(BigDecimal("200.0200"), cart.total())
        // 加另一个
        cart = gql.addCartItem(commodity.skus.last().id)
        assertEquals(listOf(BigDecimal("2.00"), BigDecimal("1.00")), cart.items.map { it.skuAmount })
        assertEquals(BigDecimal("400.0200"), cart.total())
        // 使用购物车中的商品去结算
        val purchaseOrder = gql.createPurchaseOrder(
            cartItemIds = cart.items.mapNotNull { it.id },
            address = "An address",
            postcode = "100000",
            receiverName = user.nickName,
            receiverMobile = user.mobile
        )
        assertEquals(BigDecimal("100.01"), purchaseOrder.items.first().skuSnapshot.price)
        // 把已加入订单的条目从购物车中删除
        cart = gql.cart()
        assertEquals(emptyList(), cart.items.map { it.sku().name })
        // 查看自己的购物订单
        val purchaseOrders = gql.listPurchaseOrders()
        assertEquals(listOf(purchaseOrder.id), purchaseOrders.map { it.id })
        // 填写收件信息
        val shippers = gql.listShippers()

        val shippingOrder = gql.createShippingOrder(
            purchaseOrderId = purchaseOrder.id,
            shipperId = shippers.first().id,
        )
        assertEquals(ShippingOrderStatusEnum.Created, shippingOrder.status)
        // 付款
        // 确认收货
    }

    @Test
    fun shippingE2e() {
        // 收到订单
        // 出货
        // 发货
        // 更新收货状态
    }

    @Test
    fun stockE2e() {
        // 添加 SKU
    }
}
