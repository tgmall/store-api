package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.portal.ProductCategories
import wang.ralph.store.models.portal.ProductCategory
import wang.ralph.store.models.portal.ProductCategoryTags

lateinit var productCategoryRoot: ProductCategory
lateinit var productCategoryA: ProductCategory
lateinit var productCategoryA1: ProductCategory
lateinit var productCategoryA2: ProductCategory
lateinit var productCategoryB: ProductCategory
lateinit var productCategoryB1: ProductCategory
lateinit var productCategoryB2: ProductCategory
lateinit var productCategoryA11: ProductCategory

fun initTestingCommodityData() {
    SchemaUtils.create(ProductCategories)
    SchemaUtils.create(ProductCategoryTags)
    ProductCategoryTags.deleteAll()
    ProductCategories.deleteAll()

    productCategoryRoot = ProductCategory.new {
        name = "root"
    }
    productCategoryA = ProductCategory.new {
        parent = productCategoryRoot
        name = "A"
    }
    productCategoryA1 = ProductCategory.new {
        parent = productCategoryA
        name = "A1"
    }
    productCategoryA11 = ProductCategory.new {
        parent = productCategoryA1
        name = "A11"
    }
    productCategoryA2 = ProductCategory.new {
        parent = productCategoryA
        name = "A2"
    }
    productCategoryB = ProductCategory.new {
        parent = productCategoryRoot
        name = "B"
    }
    productCategoryB1 = ProductCategory.new {
        parent = productCategoryB
        name = "B1"
    }
    productCategoryB2 = ProductCategory.new {
        parent = productCategoryB
        name = "B2"
    }
}
