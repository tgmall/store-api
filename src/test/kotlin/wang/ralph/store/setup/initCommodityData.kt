package wang.ralph.store.setup

import wang.ralph.store.models.commodity.Commodity
import wang.ralph.store.models.commodity.CommodityTag
import wang.ralph.store.models.commodity.Sku
import wang.ralph.store.models.commodity.SkuImage
import java.math.BigDecimal

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
fun initCommodityData() {
    commodityA = Commodity.new {
        name = "commodityA"
        description = "commodityA description"
    }
    skuA1 = Sku.new {
        commodity = commodityA
        name = "blueA"
        description = "blueA description"
        price = BigDecimal("100.01")
    }
    skuA2 = Sku.new {
        commodity = commodityA
        name = "greenA"
        description = "greenA description"
        price = BigDecimal("200.00")
    }
    skuImageA11 = SkuImage.new {
        smallImageUrl = "/uriA11"
        largeImageUrl = "/uriA11"
        sku = skuA1
    }
    skuImageA12 = SkuImage.new {
        smallImageUrl = "/uriA12"
        largeImageUrl = "/uriA12"
        sku = skuA1
    }
    commodityB = Commodity.new {
        name = "commodityB"
        description = "commodityB description"
    }
    skuB1 = Sku.new {
        commodity = commodityB
        name = "blueB"
        description = "blueB description"
        price = BigDecimal("1000.0")
    }
    skuB2 = Sku.new {
        commodity = commodityB
        name = "greenB"
        description = "greenB description"
        price = BigDecimal("2000.0")
    }
    skuImageB11 = SkuImage.new {
        smallImageUrl = "/uriB11"
        largeImageUrl = "/uriB11"
        sku = skuB1
    }
    skuImageB21 = SkuImage.new {
        smallImageUrl = "/uriB21"
        largeImageUrl = "/uriB21"
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
