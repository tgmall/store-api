package wang.ralph.store.setup

import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.store.models.auth.User
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
    }
}
