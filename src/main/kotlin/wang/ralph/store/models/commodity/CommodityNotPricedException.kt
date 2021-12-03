package wang.ralph.store.models.commodity

import wang.ralph.store.models.exceptions.DomainRuleViolationException

class CommodityNotPricedException(val id: String) : DomainRuleViolationException() {
    override val message: String
        get() = "Commodity with id [$id] has not yet been priced!"
}
