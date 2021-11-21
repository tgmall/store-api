package wang.ralph.store.application.purchase

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import wang.ralph.store.models.purchase.SkuSnapshot
import java.math.BigDecimal

fun SkuSnapshot.toDto(): SkuSnapshotDto =
    SkuSnapshotDto(
        id.toString(),
        name,
        description,
        unit,
        price,
        imageUris,
        commodityName,
        commodityDescription,
        commodityTags
    )

data class SkuSnapshotDto(
    val id: String,
    @GraphQLDescription("名称")
    val name: String,

    @GraphQLDescription("描述")
    val description: String,

    @GraphQLDescription("单位")
    val unit: String,

    @GraphQLDescription("价格")
    val price: BigDecimal?,

    @GraphQLDescription("图片")
    val imageUris: String,

    @GraphQLDescription("所属商品名称")
    val commodityName: String,

    @GraphQLDescription("所属商品描述")
    val commodityDescription: String,

    @GraphQLDescription("标签")
    val commodityTags: String,
)
