package org.carrefour.promotion.domain.model.exception;

public class DiscountCodeNotFoundException extends RuntimeException {
    public DiscountCodeNotFoundException( String code ) {
        super("Discount code " + code + " not found");
    }
}
