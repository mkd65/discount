package org.carrefour.promotion.domain.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Product(String id, String name, BigDecimal price) { }
