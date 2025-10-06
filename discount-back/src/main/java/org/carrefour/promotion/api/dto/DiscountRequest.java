package org.carrefour.promotion.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DiscountRequest(
        @Schema( example = "SUMMER10", description = "Discount code to apply") String code
) {}
