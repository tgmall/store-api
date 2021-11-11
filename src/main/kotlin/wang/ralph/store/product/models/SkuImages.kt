package wang.ralph.store.product.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object SkuImages : UUIDTable("sku_image") {
    val sku = reference("sku_id", Skus)
    val imageUri = varchar("image_uri", 255)
}

class SkuImage(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SkuImage>(SkuImages)

    var sku by Sku referencedOn SkuImages.sku
    var imageUri: String by SkuImages.imageUri

    fun toDto(): SkuImageDto = SkuImageDto(imageUri)
}

data class SkuImageDto(val imageUri: String)
