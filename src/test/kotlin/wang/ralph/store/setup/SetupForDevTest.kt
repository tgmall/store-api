package wang.ralph.store.setup

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.import.importTuringData
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.commodity.book.Book
import wang.ralph.store.updateAllTables
import kotlin.test.Test

class SetupForDevTest {
    @Test
    fun setupForDev() {
        setupDb()
        transaction {
            updateAllTables()
            if (User.count() == 0L) {
                initAllData()
            }
        }
        if (transaction { Book.count() } == 0L) {
            importTuringData()
        }
    }
}
