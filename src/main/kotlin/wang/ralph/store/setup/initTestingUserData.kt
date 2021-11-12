package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.auth.models.Subjects
import wang.ralph.store.auth.models.User
import wang.ralph.store.auth.models.UserCreateInput
import wang.ralph.store.auth.models.Users

lateinit var testingUser: User;
fun initTestingUserData() {
    SchemaUtils.create(Users)
    SchemaUtils.create(Subjects)
    Subjects.deleteAll()
    Users.deleteAll()
    testingUser = User.create(UserCreateInput(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    ))
}
