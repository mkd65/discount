package org.carrefour.promotion.infrastructure.mapper;


import org.carrefour.promotion.domain.model.CartItem;
import org.carrefour.promotion.domain.model.Product;
import org.carrefour.promotion.infrastructure.entity.CartItemEntity;
import org.carrefour.promotion.infrastructure.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {
    CartItemEntity toEntity( CartItem item );
    CartItem toDomain( CartItemEntity entity );
}
