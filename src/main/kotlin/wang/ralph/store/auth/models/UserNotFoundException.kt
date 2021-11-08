package wang.ralph.store.auth.models

import wang.ralph.store.common.DomainNotFoundException
import java.util.*

class UserNotFoundException(id: UUID) : DomainNotFoundException(id)
