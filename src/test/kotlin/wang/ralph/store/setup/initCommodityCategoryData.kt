package wang.ralph.store.setup

import wang.ralph.store.models.portal.CommodityCategory
import wang.ralph.store.models.portal.CommodityCategoryTag

lateinit var commodityCategoryRoot: CommodityCategory
lateinit var commodityCategoryA: CommodityCategory
lateinit var commodityCategoryA1: CommodityCategory
lateinit var commodityCategoryA2: CommodityCategory
lateinit var commodityCategoryB: CommodityCategory
lateinit var commodityCategoryB1: CommodityCategory
lateinit var commodityCategoryB2: CommodityCategory
lateinit var commodityCategoryA11: CommodityCategory

fun initCommodityCategoryData() {
    commodityCategoryRoot = CommodityCategory.new {
        name = "root"
    }
    commodityCategoryA = CommodityCategory.new {
        parent = commodityCategoryRoot
        name = "A"
    }
    commodityCategoryA1 = CommodityCategory.new {
        parent = commodityCategoryA
        name = "A1"
    }
    commodityCategoryA11 = CommodityCategory.new {
        parent = commodityCategoryA1
        name = "A11"
    }
    commodityCategoryA2 = CommodityCategory.new {
        parent = commodityCategoryA
        name = "A2"
    }
    commodityCategoryB = CommodityCategory.new {
        parent = commodityCategoryRoot
        name = "B"
    }
    commodityCategoryB1 = CommodityCategory.new {
        parent = commodityCategoryB
        name = "B1"
    }
    commodityCategoryB2 = CommodityCategory.new {
        parent = commodityCategoryB
        name = "B2"
    }

    CommodityCategoryTag.new {
        category = commodityCategoryA
        tag = "tag1"
    }
    CommodityCategoryTag.new {
        category = commodityCategoryA1
        tag = "tag2"
    }
    CommodityCategoryTag.new {
        category = commodityCategoryA11
        tag = "tag3"
    }
}
