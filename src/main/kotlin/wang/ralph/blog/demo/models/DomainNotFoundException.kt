package wang.ralph.blog.demo.models

import java.util.*

open class DomainNotFoundException(val id: UUID) : RuntimeException() {
    override val message: String
        get() = "${javaClass.simpleName} with id $id not found!"
}
