package org.carrefour.promotion.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DiscountCodeDto(
        String code,
        String type,
        BigDecimal value,
        LocalDate validFrom,
        LocalDate validTo,
        List<String> applicableProductIds
) { }
