package wang.ralph.blog.demo.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import wang.ralph.blog.demo.application.UserInput
import wang.ralph.blog.demo.application.UserService
import wang.ralph.blog.demo.models.Companies
import wang.ralph.blog.demo.models.Persons
import wang.ralph.blog.demo.models.Users
import java.util.*

lateinit var testUserId: UUID;
fun initTestDB() {
    val url = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Persons)
        SchemaUtils.create(Companies)
    }
    val service = UserService()
    testUserId = service.create(UserInput(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    ))
}
