package wang.ralph.blog.demo.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Users : UUIDTable("user") {
    val name = varchar("name", length = 64)
    val password = varchar("password", length = 128)
    val nickName = varchar("nick_name", length = 32)
    val avatarUrl = varchar("avatar)url", length = 128)
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var name: String by Users.name
    var password: String by Users.password
    var nickName: String by Users.nickName
    var avatarUrl: String by Users.avatarUrl
}
