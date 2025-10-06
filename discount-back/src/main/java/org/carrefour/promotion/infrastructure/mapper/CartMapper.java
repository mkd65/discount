package org.carrefour.promotion.infrastructure.mapper;


import org.carrefour.promotion.domain.model.Cart;
import org.carrefour.promotion.infrastructure.entity.CartEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class, DiscountCodeMapper.class})
public interface CartMapper {

    CartEntity toEntity( Cart cart );
    Cart toDomain(CartEntity entity);
}
