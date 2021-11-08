package wang.ralph.store.auth.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.auth.models.*
import java.util.*

fun setupTestingDb() {
    val url = "jdbc:h2:mem:user;DATABASE_TO_UPPER=false;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
}

lateinit var testingUserId: UUID;
fun initTestingData() {
    SchemaUtils.create(Users)
    SchemaUtils.create(Persons)
    SchemaUtils.create(Companies)
    Users.deleteAll()
    Persons.deleteAll()
    Companies.deleteAll()
    testingUserId = User.create(UserCreateInput(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    )).id.value
}
