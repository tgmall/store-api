package wang.ralph.store

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.graphql.GraphQLResponse
import wang.ralph.graphql.graphQL
import wang.ralph.store.application.dtos.UserDto
import wang.ralph.store.application.dtos.cart.CartDto
import wang.ralph.store.application.dtos.commodity.CommodityDto
import wang.ralph.store.application.dtos.portal.CommodityCategoryDto
import wang.ralph.store.application.dtos.portal.CommodityCategoryTagDto
import wang.ralph.store.setup.setupTestingDb
import java.math.BigDecimal
import kotlin.test.*

@InternalAPI
class ApplicationTest {
    companion object {
        private const val port = 55553
        private var inited = false
    }


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

    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }

    @Test
    fun testUsers() = runBlocking {
        val user: UserDto = gql(loadGraphQL("currentUser"))
        assertEquals("username", user.username)
    }

    @Test
    fun e2e() = runBlocking {
        // 注册新用户
        val user = createUser()
        assertEquals("wzc", user.username)
        // 查看商品名录
        val categories = listCommodityCategories()
        assertEquals(listOf("root"), categories.map { it.name })
        // 查看商品列表
        val commodities = listCommodities(categories.first().relatedTags.map { it.tag })
        assertEquals(listOf("commodityA", "commodityB"), commodities.map { it.name })
        // 查看商品详情
        val commodity = getCommodity(commodities.first().id)
        assertEquals(BigDecimal("100.01"), commodity.minPrice())
        assertEquals(BigDecimal("200.00"), commodity.maxPrice())
        assertEquals(listOf("blueA", "greenA"), commodity.skus.map { it.name })
        // 加入购物车
        val cart = addCartItem(commodity.skus.first().id)
        assertEquals(listOf(BigDecimal("1.00")), cart.items.map { it.amount })
        assertEquals(BigDecimal("100.0100"), cart.total())
    }

    private fun addCartItem(skuId: String, amount: BigDecimal = BigDecimal.ONE): CartDto {
        return gql(loadGraphQL("addCartItem"), mapOf("skuId" to skuId, "amount" to amount))
    }

    private fun listCommodities(tags: List<String>): List<CommodityDto> {
        return gql(loadGraphQL("commodities"), mapOf("tags" to tags))
    }

    private fun getCommodity(id: String): CommodityDto {
        return gql(loadGraphQL("commodity"), mapOf("id" to id))
    }

    private fun listCommodityCategories(): List<CommodityCategoryVo> {
        return gql(loadGraphQL("commodityCategories"))
    }

    private fun createUser(): UserDto {
        return gql(loadGraphQL("createUser"), mapOf(
            "input" to mapOf(
                "username" to "wzc",
                "password" to "password",
                "nickName" to "WZC",
                "avatarUrl" to "/wzc.svg"
            )
        ))
    }

    private inline fun <reified T> gql(query: String, variables: Map<String, Any?>? = null): T = runBlocking {
        val name = query.replace(Regex("""^(query|mutation)[\s\S]*?\{\s*(\w+)[\s\S]*$"""), "$2")
        val response: GraphQLResponse<Map<String, T>> =
            client.graphQL("http://localhost:$port/graphql", query.trimIndent(), variables) {
                val encodeBase64 = "username:password".encodeBase64()
                header("Authorization", "Basic $encodeBase64")
            }
        assertNull(response.errors)
        assertNull(response.extensions)
        assertNotNull(response.data)
        response.data!![name]!!
    }

    private fun loadGraphQL(filename: String): String {
        return javaClass.getResourceAsStream("$filename.graphql")!!.reader(Charsets.UTF_8).readText()
    }
}

data class CommodityCategoryVo(
    val id: String,
    val name: String,
    val children: List<CommodityCategoryDto> = emptyList(),
    val tags: List<CommodityCategoryTagDto> = emptyList(),
    val descentTags: List<CommodityCategoryTagDto> = emptyList(),
    val relatedTags: List<CommodityCategoryTagDto> = emptyList(),
)
