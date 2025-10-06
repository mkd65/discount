package org.carrefour.promotion.application;

import org.carrefour.promotion.domain.model.Cart;
import org.carrefour.promotion.domain.model.CartItem;
import org.carrefour.promotion.domain.model.DiscountCode;
import org.carrefour.promotion.domain.model.Product;
import org.carrefour.promotion.domain.model.exception.CartNotFoundException;
import org.carrefour.promotion.domain.model.exception.DiscountCodeNotFoundException;
import org.carrefour.promotion.infrastructure.entity.CartEntity;
import org.carrefour.promotion.infrastructure.entity.DiscountCodeEntity;
import org.carrefour.promotion.infrastructure.mapper.CartMapper;
import org.carrefour.promotion.infrastructure.mapper.DiscountCodeMapper;
import org.carrefour.promotion.infrastructure.repository.CartRepository;
import org.carrefour.promotion.infrastructure.repository.DiscountCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplyDiscountServiceTest {

    @Mock
    private CartRepository         cartRepository;
    @Mock
    private DiscountCodeRepository discountRepository;
    @Mock
    private CartMapper             cartMapper;
    @Mock
    private DiscountCodeMapper     discountCodeMapper;

    @InjectMocks
    private ApplyDiscountService applyDiscountService;

    private Cart               cart;
    private CartEntity         cartEntity;
    private DiscountCode       discountCode;
    private DiscountCodeEntity discountEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );

        cart = Cart.builder()
                   .id( "cart123" )
                   .items( List.of(
                           CartItem.builder()
                                   .product( Product.builder()
                                                     .id( "PA" )
                                                    .name( "PA" )
                                                    .price( BigDecimal.TEN )
                                                    .build() )
                                   .quantity( 2 )
                                   .build()
                                  ) )
                   .build();
        discountCode = DiscountCode.builder()
                                   .code( "PROMO10" )
                                   .value( BigDecimal.TEN )
                                   .type( DiscountCode.DiscountType.PERCENTAGE )
                                   .applicableProductIds( Set.of( "PA" ) )
                                   .validFrom( java.time.LocalDate.now()
                                                                  .minusDays( 1 ) )
                                   .validTo( java.time.LocalDate.now()
                                                                .plusDays( 10 ) )
                                   .build();

        cartEntity = new CartEntity();
        discountEntity = new DiscountCodeEntity();
    }

    @Test
    void applyDiscount_shouldApplyAndSaveDiscountSuccessfully() {
        // Given
        when( cartRepository.findById( "cart123" ) ).thenReturn( Optional.of( cartEntity ) );
        when( cartMapper.toDomain( cartEntity ) ).thenReturn( cart );

        when( discountRepository.findByCode( "PROMO10" ) ).thenReturn( Optional.of( discountEntity ) );
        when( discountCodeMapper.toDomain( discountEntity ) ).thenReturn( discountCode );

        when( cartMapper.toEntity( any( Cart.class ) ) ).thenReturn( cartEntity );
        when( cartRepository.save( cartEntity ) ).thenReturn( cartEntity );
        when( cartMapper.toDomain( cartEntity ) ).thenReturn( cart );

        // When
        Cart result = applyDiscountService.applyDiscount( "cart123", "PROMO10" );

        // Then
        assertThat( result ).isNotNull();
        verify( cartRepository ).findById( "cart123" );
        verify( discountRepository ).findByCode( "PROMO10" );
        verify( cartRepository ).save( any() );
    }

    @Test
    void applyDiscount_shouldThrowCartNotFoundException() {
        when( cartRepository.findById( "missingCart" ) ).thenReturn( Optional.empty() );
        assertThrows( CartNotFoundException.class,
                      () -> applyDiscountService.applyDiscount( "missingCart", "PROMO10" ) );
    }

    @Test
    void applyDiscount_shouldThrowDiscountCodeNotFoundException() {
        when( cartRepository.findById( "cart123" ) ).thenReturn( Optional.of( cartEntity ) );
        when( cartMapper.toDomain( cartEntity ) ).thenReturn( cart );
        when( discountRepository.findByCode( "INVALID" ) ).thenReturn( Optional.empty() );

        assertThrows( DiscountCodeNotFoundException.class,
                      () -> applyDiscountService.applyDiscount( "cart123", "INVALID" ) );
    }

    @Test
    void getCart_shouldReturnCartWithoutDiscount() {
        when( cartRepository.findById( "cart123" ) ).thenReturn( Optional.of( cartEntity ) );
        when( cartMapper.toDomain( cartEntity ) ).thenReturn( cart );

        Cart result = applyDiscountService.getCart( "cart123" );

        assertThat( result ).isEqualTo( cart );
        verify( cartRepository, never() ).save( any() );
    }

    @Test
    void getCart_shouldApplyExistingDiscountAndSave() {
        cart.setDiscountCode( discountCode );

        when( cartRepository.findById( "cart123" ) ).thenReturn( Optional.of( cartEntity ) );
        when( cartMapper.toDomain( cartEntity ) ).thenReturn( cart );
        when( cartMapper.toEntity( cart ) ).thenReturn( cartEntity );

        Cart result = applyDiscountService.getCart( "cart123" );

        assertThat( result ).isNotNull();
        verify( cartRepository ).save( cartEntity );
    }

    @Test
    void getCart_shouldThrowCartNotFoundException() {
        when( cartRepository.findById( "missing" ) ).thenReturn( Optional.empty() );
        assertThrows( CartNotFoundException.class, () -> applyDiscountService.getCart( "missing" ) );
    }
}
