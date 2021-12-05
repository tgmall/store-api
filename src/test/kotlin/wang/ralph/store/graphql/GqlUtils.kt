package wang.ralph.store.graphql

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import wang.ralph.graphql.GraphQLResponse
import wang.ralph.graphql.graphQL
import wang.ralph.store.application.auth.UserDto
import wang.ralph.store.application.cart.CartDto
import wang.ralph.store.application.commodity.CommodityDto
import wang.ralph.store.application.portal.CommodityCategoryDto
import wang.ralph.store.application.portal.CommodityCategoryTagDto
import wang.ralph.store.application.purchase.PurchaseOrderDto
import wang.ralph.store.application.shipping.ShipperDto
import wang.ralph.store.application.shipping.ShippingOrderDto
import wang.ralph.store.application.stock.StockDto
import java.math.BigDecimal
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@InternalAPI
class GqlUtils(val port: Int) {
    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }

    var jwt: String? = null

    suspend fun loginAs(mobile: String, password: String) {
        data class LoginRequest(val mobile: String, val password: String)
        data class LoginResponse(val userId: String, val jwt: String)

        val response: LoginResponse = client.post("http://localhost:${port}/jwt") {
            contentType(ContentType.Application.Json)
            body = LoginRequest(mobile, password)
        }
        jwt = response.jwt
    }

    fun cart(): CartDto {
        return execute(loadResource("cart"))
    }

    fun addCartItem(skuId: String, skuAmount: BigDecimal = BigDecimal.ONE): CartDto {
        return execute(loadResource("addCartItem"), mapOf("skuId" to skuId, "skuAmount" to skuAmount))
    }

    fun createPurchaseOrder(
        cartItemIds: List<String>,
        receiverName: String?,
        receiverMobile: String,
        address: String,
        postcode: String,
    ): PurchaseOrderDto {
        return execute(loadResource("createPurchaseOrder"), mapOf(
            "cartItemIds" to cartItemIds,
            "receiverName" to receiverName,
            "receiverMobile" to receiverMobile,
            "address" to address,
            "postcode" to postcode,
        ))
    }

    fun listShippers(): List<ShipperDto> {
        return execute(loadResource("shippers"))
    }

    fun createShippingOrder(
        purchaseOrderId: String,
        shipperId: String,
    ): ShippingOrderDto {
        return execute(loadResource("createShippingOrder"), mapOf(
            "purchaseOrderId" to purchaseOrderId,
            "shipperId" to shipperId,
        ))
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

    data class CaptchaDto(val id: String, val imageUrl: String)

    fun createCaptcha(): CaptchaDto {
        return execute(loadResource("createCaptcha"))
    }

    fun sendCodeViaSms(
        mobile: String,
        captchaId: String,
        captchaValue: String,
    ): String {
        return execute(loadResource("sendCodeViaSms"), mapOf(
            "mobile" to mobile,
            "captchaId" to captchaId,
            "captchaValue" to captchaValue,
        ))
    }

    fun register(mobile: String, password: String): UserDto {
        return execute(loadResource("register"), mapOf(
            "mobile" to mobile,
            "password" to password,
            "smsCode" to "123456",
        ))
    }

    fun updateMyProfile(
        name: String? = null,
        nickName: String? = null,
        avatarUrl: String? = null,
    ): UserDto {
        return execute(loadResource("updateMyProfile"), mapOf(
            "name" to name,
            "nickName" to nickName,
            "avatarUrl" to avatarUrl,
        ))
    }

    fun getStock(skuId: String): StockDto {
        return execute(loadResource("stock"), mapOf("skuId" to skuId))
    }

    fun listPurchaseOrders(): List<PurchaseOrderDto> {
        return execute(loadResource("purchaseOrders"))
    }

    private inline fun <reified T> execute(query: String, variables: Map<String, Any?>? = null): T = runBlocking {
        val name = query.replace(Regex("""^(query|mutation)[\s\S]*?\{\s*(\w+)[\s\S]*$"""), "$2")
        val response: GraphQLResponse<Map<String, T>> =
            client.graphQL("http://localhost:${port}/graphql", query.trimIndent(), variables) {
                if (jwt != null) {
                    header("Authorization", "Bearer $jwt")
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
