package org.carrefour.promotion.domain.model;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItem (Product product, int quantity) {
    public BigDecimal totalPrice() {
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }
}
