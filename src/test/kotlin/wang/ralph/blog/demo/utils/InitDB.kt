package wang.ralph.blog.demo.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.models.Users

fun initTestDB() {
    val url = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
        Users.deleteAll()
    }
}
