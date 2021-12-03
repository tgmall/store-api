package wang.ralph.store.models.auth

import wang.ralph.store.models.exceptions.DomainNotFoundException
import java.util.*

class UserNotFoundException(id: UUID) : DomainNotFoundException(id)
