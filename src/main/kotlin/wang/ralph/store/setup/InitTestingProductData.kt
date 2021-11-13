package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.product.*

lateinit var productA: Product
lateinit var skuA1: Sku
lateinit var skuA2: Sku
lateinit var skuImageA11: SkuImage
lateinit var skuImageA12: SkuImage
lateinit var productB: Product
lateinit var skuB1: Sku
lateinit var skuB2: Sku
lateinit var skuImageB11: SkuImage
lateinit var skuImageB21: SkuImage

fun initTestingProductData() {
    SchemaUtils.create(Products)
    SchemaUtils.create(Skus)
    SchemaUtils.create(SkuImages)
    SchemaUtils.create(ProductTags)
    ProductTags.deleteAll()
    SkuImages.deleteAll()
    Skus.deleteAll()
    Products.deleteAll()

    productA = Product.new {
        name = "productA"
        description = "productA description"
    }
    skuA1 = Sku.new {
        product = productA
        name = "blueA"
        description = "blueA description"
    }
    skuA2 = Sku.new {
        product = productA
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
    productB = Product.new {
        name = "productB"
        description = "productB description"
    }
    skuB1 = Sku.new {
        name = "blueB"
        description = "blueB description"
        product = productB
    }
    skuB2 = Sku.new {
        product = productB
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
    ProductTag.new {
        product = productA
        tag = "tag1"
    }
    ProductTag.new {
        product = productA
        tag = "tag2"
    }
    ProductTag.new {
        product = productB
        tag = "tag1"
    }
}
