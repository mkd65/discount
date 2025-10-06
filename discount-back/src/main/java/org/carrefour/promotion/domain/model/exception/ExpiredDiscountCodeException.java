package org.carrefour.promotion.domain.model.exception;

public class ExpiredDiscountCodeException extends RuntimeException {
    public ExpiredDiscountCodeException( final String code ) {
        super( "The discount code " + code + " is expired." );
    }
}
