package wang.ralph.store.import.dto

import java.math.BigDecimal

class TuringBookSalesInfoDto {
    var edition: Int = 0
    var canBeSaled: Boolean = false
    var price: BigDecimal = BigDecimal.ZERO
    var discount: Int = 0
    var discountPrice: BigDecimal = BigDecimal.ZERO
}
