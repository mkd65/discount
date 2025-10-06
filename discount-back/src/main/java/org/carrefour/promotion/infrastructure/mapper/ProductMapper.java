package org.carrefour.promotion.infrastructure.mapper;


import org.carrefour.promotion.domain.model.Product;
import org.carrefour.promotion.infrastructure.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
    ProductEntity toEntity( Product product );
    Product toDomain( ProductEntity entity );
}
