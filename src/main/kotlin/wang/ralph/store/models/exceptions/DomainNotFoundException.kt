package wang.ralph.store.models.exceptions

import java.util.*

open class DomainNotFoundException(val id: UUID) : DomainException() {
    override val message: String
        get() = "${javaClass.simpleName} with id [$id] not found!"
}
