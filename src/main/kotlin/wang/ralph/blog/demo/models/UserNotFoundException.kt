package wang.ralph.blog.demo.models

import java.util.*

class UserNotFoundException(id: UUID) : DomainNotFoundException(id)
