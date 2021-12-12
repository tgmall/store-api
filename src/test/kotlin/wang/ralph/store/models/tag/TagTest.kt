package wang.ralph.store.models.tag

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.recreateAllTables
import wang.ralph.store.setup.initTagData
import wang.ralph.store.setup.setupTestingDb
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TagTest {
    @BeforeTest
    fun setup() {
        setupTestingDb()
        transaction {
            recreateAllTables()
            initTagData()
        }
    }

    @Test
    fun create() = transaction {
        val all = Tag.all()
        assertEquals(listOf("tag1", "tag2", "tag3", "tag4"), all.map { it.tag })
    }
}
