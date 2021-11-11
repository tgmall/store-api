package wang.ralph.store.setup

import org.jetbrains.exposed.sql.Database

fun setupTestingDb() {
    val url = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
}
