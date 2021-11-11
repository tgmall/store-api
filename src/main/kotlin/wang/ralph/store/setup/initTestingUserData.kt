package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.auth.models.*

lateinit var testingUser: User;
fun initTestingUserData() {
    SchemaUtils.create(Users)
    SchemaUtils.create(Persons)
    SchemaUtils.create(Companies)
    Persons.deleteAll()
    Companies.deleteAll()
    Users.deleteAll()
    testingUser = User.create(UserCreateInput(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    ))
}
