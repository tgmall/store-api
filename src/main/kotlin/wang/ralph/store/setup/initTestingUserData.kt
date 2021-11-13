package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import wang.ralph.store.models.auth.Subjects
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.auth.Users

lateinit var testingUser: User;
fun initTestingUserData() {
    SchemaUtils.create(Users)
    SchemaUtils.create(Subjects)
    Subjects.deleteAll()
    Users.deleteAll()
    testingUser = User.create(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    )
}
