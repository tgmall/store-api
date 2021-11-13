package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.commodity.*

lateinit var commodityA: Commodity
lateinit var skuA1: Sku
lateinit var skuA2: Sku
lateinit var skuImageA11: SkuImage
lateinit var skuImageA12: SkuImage
lateinit var commodityB: Commodity
lateinit var skuB1: Sku
lateinit var skuB2: Sku
lateinit var skuImageB11: SkuImage
lateinit var skuImageB21: SkuImage

fun initTestingCommodityData() {
    SchemaUtils.create(Commodities)
    SchemaUtils.create(Skus)
    SchemaUtils.create(SkuImages)
    SchemaUtils.create(CommodityTags)
    CommodityTags.deleteAll()
    SkuImages.deleteAll()
    Skus.deleteAll()
    Commodities.deleteAll()

    commodityA = Commodity.new {
        name = "commodityA"
        description = "commodityA description"
    }
    skuA1 = Sku.new {
        commodity = commodityA
        name = "blueA"
        description = "blueA description"
    }
    skuA2 = Sku.new {
        commodity = commodityA
        name = "greenA"
        description = "greenA description"
    }
    skuImageA11 = SkuImage.new {
        imageUri = "/uriA11"
        sku = skuA1
    }
    skuImageA12 = SkuImage.new {
        imageUri = "/uriA12"
        sku = skuA1
    }
    commodityB = Commodity.new {
        name = "commodityB"
        description = "commodityB description"
    }
    skuB1 = Sku.new {
        name = "blueB"
        description = "blueB description"
        commodity = commodityB
    }
    skuB2 = Sku.new {
        commodity = commodityB
        name = "greenB"
        description = "greenB description"
    }
    skuImageB11 = SkuImage.new {
        imageUri = "/uriB11"
        sku = skuB1
    }
    skuImageB21 = SkuImage.new {
        imageUri = "/uriB21"
        sku = skuB2
    }
    CommodityTag.new {
        commodity = commodityA
        tag = "tag1"
    }
    CommodityTag.new {
        commodity = commodityA
        tag = "tag2"
    }
    CommodityTag.new {
        commodity = commodityB
        tag = "tag1"
    }
}
