package org.carrefour.promotion.api.dto;

import java.math.BigDecimal;

public record CartItemDto(ProductDto product, int quantity, BigDecimal totalPrice) { }

