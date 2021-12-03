package wang.ralph.store.models.auth

import wang.ralph.store.models.exceptions.DomainException

class UserMobileExistsException(mobile: String) : DomainException() {

}
