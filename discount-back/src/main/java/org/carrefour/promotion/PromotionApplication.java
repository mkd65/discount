package org.carrefour.promotion;

import org.carrefour.promotion.infrastructure.entity.CartEntity;
import org.carrefour.promotion.infrastructure.entity.CartItemEntity;
import org.carrefour.promotion.infrastructure.entity.DiscountCodeEntity;
import org.carrefour.promotion.infrastructure.entity.ProductEntity;
import org.carrefour.promotion.infrastructure.repository.CartRepository;
import org.carrefour.promotion.infrastructure.repository.DiscountCodeRepository;
import org.carrefour.promotion.infrastructure.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EntityScan(basePackages = "org.carrefour.promotion.infrastructure.entity")  // Adjust this package as needed
@EnableJpaRepositories(basePackages = "org.carrefour.promotion.infrastructure.repository")
public class PromotionApplication implements CommandLineRunner {

    private final CartRepository         cartRepository;
    private final ProductRepository      productRepository;
    private final DiscountCodeRepository discountCodeRepository;

    public PromotionApplication( final CartRepository cartRepository, final ProductRepository productRepository, final DiscountCodeRepository discountCodeRepository ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.discountCodeRepository = discountCodeRepository;
    }


    public static void main( String[] args ) {
        SpringApplication.run( PromotionApplication.class, args );
    }

    @Override
    public void run( final String... args ) throws Exception {
        cartRepository.deleteAll();
        productRepository.deleteAll();
        discountCodeRepository.deleteAll();

        productRepository.saveAll( List.of( ProductEntity.builder()
                                                         .id( "P1" )
                                                         .name( "Product 1" )
                                                         .price( java.math.BigDecimal.valueOf( 100 ) )
                                                         .build(),
                                            ProductEntity.builder()
                                                         .id( "P2" )
                                                         .name( "Product 2" )
                                                         .price( java.math.BigDecimal.valueOf( 200 ) )
                                                         .build(),
                                            ProductEntity.builder()
                                                         .id( "P3" )
                                                         .name( "Product 3" )
                                                         .price( java.math.BigDecimal.valueOf( 300 ) )
                                                         .build()
                                          ) );

        discountCodeRepository.saveAll( List.of( DiscountCodeEntity.builder()
                                                                   .code( "DISC10" )
                                                                   .value( BigDecimal.valueOf( 10 ) )
                                                                   .validFrom( java.time.LocalDate.now().minusDays(1) )
                                                                   .validTo( java.time.LocalDate.now().plusDays(10) )
                                                                   .type( DiscountCodeEntity.DiscountType.PERCENTAGE )
                                                                   .applicableProductIds( Set.of( "P1", "P2" ) )
                                                                   .build(),
                                                 DiscountCodeEntity.builder()
                                                                   .code( "DISC15" )
                                                         .validFrom( java.time.LocalDate.now().minusDays(1) )
                                                         .validTo( java.time.LocalDate.now().plusDays(10) )
                                                                   .value( BigDecimal.valueOf( 15 ) )
                                                                   .type( DiscountCodeEntity.DiscountType.AMOUNT )
                                                                   .applicableProductIds( Set.of( "P1", "P3" ) )
                                                                   .build()
                                               ) );



        CartEntity cart = CartEntity.builder()
                                    .id("1")
                .discountCode( discountCodeRepository.findById( "DISC10" ).orElseThrow() )
                                    .build();

        CartItemEntity item1 = CartItemEntity.builder()
                                             .product(productRepository.findById("P1").orElseThrow())
                                             .quantity(2)
                                             .cart(cart)
                                             .build();

        cart.setItems(List.of(item1));
        cartRepository.save(cart);

    }
}
