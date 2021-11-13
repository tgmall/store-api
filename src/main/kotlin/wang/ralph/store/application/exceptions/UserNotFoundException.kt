package wang.ralph.store.application.exceptions

import java.util.*

class UserNotFoundException(id: UUID) : DomainNotFoundException(id)
