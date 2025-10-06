package org.carrefour.promotion.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDTO(
        List<CartItemDto> items,
        DiscountCodeDto discountCode,
        BigDecimal totalBeforeDiscount,
        BigDecimal totalAfterDiscount
) { }
