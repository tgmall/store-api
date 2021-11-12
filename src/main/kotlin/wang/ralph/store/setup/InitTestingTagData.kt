package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.tag.models.Tag
import wang.ralph.store.tag.models.Tags

lateinit var tag1: Tag
lateinit var tag2: Tag
lateinit var tag3: Tag
lateinit var tag4: Tag

fun initTestingTagData() {
    SchemaUtils.create(Tags)
    Tags.deleteAll()

    tag1 = Tag.newProductTag("tag1")
    tag2 = Tag.newProductTag("tag2")
    tag3 = Tag.newProductTag("tag3")
    tag4 = Tag.newProductTag("tag4")
}
