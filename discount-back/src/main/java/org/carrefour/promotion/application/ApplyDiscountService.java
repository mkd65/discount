package org.carrefour.promotion.application;

import lombok.RequiredArgsConstructor;
import org.carrefour.promotion.domain.model.exception.CartNotFoundException;
import org.carrefour.promotion.domain.model.exception.DiscountCodeNotFoundException;
import org.carrefour.promotion.infrastructure.mapper.CartMapper;
import org.carrefour.promotion.infrastructure.mapper.DiscountCodeMapper;
import org.carrefour.promotion.infrastructure.repository.CartRepository;
import org.carrefour.promotion.infrastructure.repository.DiscountCodeRepository;
import org.carrefour.promotion.domain.model.Cart;
import org.carrefour.promotion.domain.model.DiscountCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyDiscountService {

    private final CartRepository         cartRepository;
    private final        DiscountCodeRepository                                   discountRepository;
    private final CartMapper         cartMapper;
    private final DiscountCodeMapper discountCodeMapper;


    public Cart applyDiscount( String cartId, String discountCode ) {
        Cart cart = cartRepository.findById(cartId).map( cartMapper::toDomain )
                                  .orElseThrow(() -> new CartNotFoundException( cartId) );

        DiscountCode discount = discountRepository.findByCode( discountCode ).map( discountCodeMapper::toDomain )
                                                  .orElseThrow(() -> new DiscountCodeNotFoundException( discountCode ) );

        cart.applyDiscount(discount);
        cart = cartMapper.toDomain( cartRepository.save(cartMapper.toEntity(cart)) );
        return cart;
    }

    public Cart getCart( final String cartId ) {
        Cart cart = cartRepository.findById(cartId).map( cartMapper::toDomain )
                .orElseThrow(() -> new CartNotFoundException( cartId) );
        if(cart.getDiscountCode() != null){
            cart.applyDiscount(cart.getDiscountCode());
            cartRepository.save( cartMapper.toEntity( cart ) );
        }
        return cart;
    }
}
