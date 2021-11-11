package wang.ralph.store.auth.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.auth.models.*
import wang.ralph.store.cart.models.CartItems
import wang.ralph.store.cart.models.Carts

fun setupTestingDb() {
    val url = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    val driver = "org.h2.Driver"
    Database.connect(url, driver)
}

lateinit var testingUser: User;
fun initTestingData() {
    SchemaUtils.create(Users)
    SchemaUtils.create(Persons)
    SchemaUtils.create(Companies)
    SchemaUtils.create(Carts)
    SchemaUtils.create(CartItems)
    Persons.deleteAll()
    Companies.deleteAll()
    Users.deleteAll()
    CartItems.deleteAll()
    Carts.deleteAll()
    testingUser = User.create(UserCreateInput(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    ))
}
