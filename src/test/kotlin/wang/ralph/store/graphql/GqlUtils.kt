package wang.ralph.store.graphql

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import wang.ralph.graphql.GraphQLResponse
import wang.ralph.graphql.graphQL
import wang.ralph.store.application.cart.CartDto
import wang.ralph.store.application.commodity.CommodityDto
import wang.ralph.store.application.dtos.UserDto
import wang.ralph.store.application.portal.CommodityCategoryDto
import wang.ralph.store.application.portal.CommodityCategoryTagDto
import wang.ralph.store.application.purchase.PurchaseOrderDto
import java.math.BigDecimal
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@InternalAPI
class GqlUtils(val port: Int) {
    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }
    private var username: String? = null
    private var password: String? = null

    fun loginAs(username: String, password: String) {
        this.username = username
        this.password = password
    }

    fun cart(): CartDto {
        return execute(loadResource("cart"))
    }

    fun addCartItem(skuId: String, skuAmount: BigDecimal = BigDecimal.ONE): CartDto {
        return execute(loadResource("addCartItem"), mapOf("skuId" to skuId, "skuAmount" to skuAmount))
    }

    fun createPurchaseOrder(cartItemIds: List<String>): PurchaseOrderDto {
        return execute(loadResource("createPurchaseOrder"), mapOf("cartItemIds" to cartItemIds))
    }

    fun listCommodities(tags: List<String>): List<CommodityDto> {
        return execute(loadResource("commodities"), mapOf("tags" to tags))
    }

    fun getCommodity(id: String): CommodityDto {
        return execute(loadResource("commodity"), mapOf("id" to id))
    }

    fun listCommodityCategories(): List<CommodityCategoryVo> {
        return execute(loadResource("commodityCategories"))
    }

    fun createUser(username: String, password: String): UserDto {
        return execute(loadResource("createUser"), mapOf(
            "input" to mapOf(
                "username" to username,
                "password" to password,
                "nickName" to username.uppercase(),
                "avatarUrl" to "/files/$username.svg"
            )
        ))
    }

    private inline fun <reified T> execute(query: String, variables: Map<String, Any?>? = null): T = runBlocking {
        val name = query.replace(Regex("""^(query|mutation)[\s\S]*?\{\s*(\w+)[\s\S]*$"""), "$2")
        val response: GraphQLResponse<Map<String, T>> =
            client.graphQL("http://localhost:${port}/graphql", query.trimIndent(), variables) {
                if (username != null) {
                    val encodeBase64 = "$username:$password".encodeBase64()
                    header("Authorization", "Basic $encodeBase64")
                }
            }
        assertNull(response.errors)
        assertNull(response.extensions)
        assertNotNull(response.data)
        response.data!![name]!!
    }

    private fun loadResource(filename: String): String {
        return javaClass.getResourceAsStream("$filename.graphql")!!.reader(Charsets.UTF_8).readText()
    }

    fun getCurrentUser(): UserDto {
        return execute(loadResource("currentUser"))
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
