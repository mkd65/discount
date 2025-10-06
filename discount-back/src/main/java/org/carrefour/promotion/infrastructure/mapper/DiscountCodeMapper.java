package org.carrefour.promotion.infrastructure.mapper;


import org.carrefour.promotion.domain.model.CartItem;
import org.carrefour.promotion.domain.model.DiscountCode;
import org.carrefour.promotion.infrastructure.entity.CartItemEntity;
import org.carrefour.promotion.infrastructure.entity.DiscountCodeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountCodeMapper {
    DiscountCodeEntity toEntity( DiscountCode discountCode );
    DiscountCode toDomain(DiscountCodeEntity entity);
}
