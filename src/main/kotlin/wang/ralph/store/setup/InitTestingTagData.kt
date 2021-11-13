package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.tag.Tag
import wang.ralph.store.models.tag.Tags

lateinit var tag1: Tag
lateinit var tag2: Tag
lateinit var tag3: Tag
lateinit var tag4: Tag

fun initTestingTagData() {
    SchemaUtils.create(Tags)
    Tags.deleteAll()

    tag1 = Tag.newCommodityTag("tag1")
    tag2 = Tag.newCommodityTag("tag2")
    tag3 = Tag.newCommodityTag("tag3")
    tag4 = Tag.newCommodityTag("tag4")
}
