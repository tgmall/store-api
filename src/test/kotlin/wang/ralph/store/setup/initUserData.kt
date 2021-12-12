package wang.ralph.store.setup

import wang.ralph.store.models.auth.Subject
import wang.ralph.store.models.auth.User
import wang.ralph.store.models.auth.encodePassword
import java.util.*

lateinit var testingUser: User
fun initUserData() {
    val subject = Subject.newPerson()

    val id = UUID.fromString("00000000-0000-0000-0000-000000000001")
    val mobile = "13333333333"
    val password = "password"

    testingUser = User.new(id) {
        this.subject = subject
        this.mobile = mobile
        this.encodedPassword = encodePassword(password)
    }
}
