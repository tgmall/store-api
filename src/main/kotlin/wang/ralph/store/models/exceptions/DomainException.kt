package wang.ralph.store.models.exceptions

abstract class DomainException : RuntimeException() {
    val code get() = javaClass.simpleName.replace(Regex("Exception$"), "")
}
