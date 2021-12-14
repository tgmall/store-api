package wang.ralph.store.models.commodity.book

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Presses : UUIDTable("press") {
    val name = varchar("name", 32)
}

class Press(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Press>(Presses) {
        fun findByName(pressName: String): Press? {
            return find { Presses.name eq pressName }.firstOrNull()
        }
    }

    var name by Presses.name
    val books by Book optionalReferrersOn Books.press
}
