package org.carrefour.promotion.domain.model;

public record DiscountApplicationResult(
        String cartId,
        String discountCode,
        double discountAmount,
        double totalAfterDiscount
) {}
