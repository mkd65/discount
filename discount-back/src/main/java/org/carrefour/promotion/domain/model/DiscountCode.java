package org.carrefour.promotion.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
public record DiscountCode(
        String code,
        DiscountType type,
        BigDecimal value,
        LocalDate validFrom,
        LocalDate validTo,
        Set<String> applicableProductIds
) {

    public enum DiscountType { PERCENTAGE, AMOUNT }

}
