package wang.ralph.store.models.commodity

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("商品类型")
enum class CommodityType {
    @GraphQLDescription("默认")
    Default,

    @GraphQLDescription("书籍")
    Book,
}
