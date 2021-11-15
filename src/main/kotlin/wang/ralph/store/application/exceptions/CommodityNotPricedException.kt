package wang.ralph.store.application.exceptions

class CommodityNotPricedException(val id: String) : DomainRuleViolationException() {
    override val message: String
        get() = "Commodity with id [$id] has not yet been priced!"
}
