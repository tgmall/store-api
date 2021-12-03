package wang.ralph.store.models.exceptions

abstract class DomainRuleViolationException : RuntimeException() {
    override val message: String
        get() = "Domain logic is violated here"
}
