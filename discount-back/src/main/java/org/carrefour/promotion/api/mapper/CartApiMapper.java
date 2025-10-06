package org.carrefour.promotion.api.mapper;

import org.carrefour.promotion.api.dto.*;
import org.carrefour.promotion.domain.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartApiMapper {

    CartApiMapper INSTANCE = Mappers.getMapper(CartApiMapper.class);

    // --- Product
    ProductDto toDto( Product product );
    Product toDomain(ProductDto dto);

    // --- Discount
    @Mapping(target = "type", source = "type", qualifiedByName = "mapDiscountTypeToString")
    DiscountCodeDto toDto(DiscountCode code);

    @Mapping(target = "type", source = "type", qualifiedByName = "mapStringToDiscountType")
    DiscountCode toDomain(DiscountCodeDto dto);

    // --- Cart Item
    @Mapping(target = "totalPrice", expression = "java(item.totalPrice())")
    CartItemDto toDto( CartItem item );
    CartItem toDomain(CartItemDto dto);

    // --- Cart
    @Mapping(target = "totalBeforeDiscount", expression = "java(cart.totalBeforeDiscount())")
    @Mapping(target = "totalAfterDiscount", expression = "java(cart.totalAfterDiscount())")
    CartDTO toDto(Cart cart);
    Cart toDomain(CartDTO dto);

    // --- Custom type conversions
    @Named("mapDiscountTypeToString")
    default String mapDiscountTypeToString( DiscountCode.DiscountType type ) {
        return type == null ? null : type.name();
    }

    @Named("mapStringToDiscountType")
    default DiscountCode.DiscountType mapStringToDiscountType( String type ) {
        return type == null ? null : DiscountCode.DiscountType.valueOf( type );
    }
}
