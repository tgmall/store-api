package wang.ralph.store.models.commodity

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object SkuImages : UUIDTable("sku_image") {
    val sku = reference("sku_id", Skus)
    val smallImageUrl = varchar("small_image_url", 255)
    val largeImageUrl = varchar("large_image_url", 255)
}

// SKU 图片
class SkuImage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SkuImage>(SkuImages)

    // 所属 SKU
    var sku by Sku referencedOn SkuImages.sku

    // 图片的 URI
    var largeImageUrl by SkuImages.largeImageUrl

    // 图片的 URI
    var smallImageUrl by SkuImages.smallImageUrl

}

