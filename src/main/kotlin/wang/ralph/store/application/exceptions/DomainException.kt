package wang.ralph.store.application.exceptions

abstract class DomainException : RuntimeException() {
    val code get() = javaClass.simpleName.replace(Regex("Exception$"), "")
}
