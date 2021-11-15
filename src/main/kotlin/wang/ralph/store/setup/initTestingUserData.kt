package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.auth.Subjects
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.auth.Users

lateinit var testingUser: User;
fun initTestingUserData() {
    SchemaUtils.drop(Subjects)
    SchemaUtils.drop(Users)
    SchemaUtils.create(Users)
    SchemaUtils.create(Subjects)
    testingUser = User.create(
        username = "username",
        password = "password",
        nickName = "nickName",
        avatarUrl = "avatarUrl",
    )
}
