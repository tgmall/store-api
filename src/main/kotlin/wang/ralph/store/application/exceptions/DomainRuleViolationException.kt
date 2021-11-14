package wang.ralph.store.application.exceptions

abstract class DomainRuleViolationException : RuntimeException() {
    override val message: String
        get() = "Domain logic is violated here"
}
