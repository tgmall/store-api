package wang.ralph.store.models.commodity

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object SkuImages : UUIDTable("sku_image") {
    val sku = reference("sku_id", Skus)
    val imageUri = varchar("image_uri", 255)
}

@GraphQLDescription("SKU 图片")
class SkuImage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SkuImage>(SkuImages)

    @GraphQLDescription("所属 SKU")
    var sku by Sku referencedOn SkuImages.sku

    @GraphQLDescription("图片的 URI")
    var imageUri: String by SkuImages.imageUri

}

