package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.auth.Contacts
import wang.ralph.store.models.auth.Subjects
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.auth.Users

lateinit var testingUser: User;
fun initTestingUserData() {
    SchemaUtils.drop(Contacts)
    SchemaUtils.drop(Users)
    SchemaUtils.drop(Subjects)
    SchemaUtils.create(Subjects)
    SchemaUtils.create(Users)
    SchemaUtils.create(Contacts)

    testingUser = User.register(
        mobile = "13333333333",
        password = "password",
    )
}
