package org.carrefour.promotion.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Cart {
    private String           id;
    private List<CartItem> items;
    private DiscountCode   discountCode;
    private BigDecimal     totalBeforeDiscount;
    private BigDecimal     totalAfterDiscount;


    public BigDecimal totalBeforeDiscount() {
        if ( items == null || items.isEmpty() ) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                    .map( CartItem::totalPrice )
                    .reduce( BigDecimal.ZERO, BigDecimal::add );
    }

    public BigDecimal totalAfterDiscount() {
        return totalAfterDiscount != null ? totalAfterDiscount : totalBeforeDiscount();
    }


    public void applyDiscount( DiscountCode discountCode ) {
        var today = java.time.LocalDate.now();

        if ( today.isBefore( discountCode.validFrom() ) || today.isAfter( discountCode.validTo() ) ) {
            throw new IllegalArgumentException( "Discount code not valid today" );
        }

        var totalApplicable = items.stream()
                                   .filter( item -> discountCode.applicableProductIds()
                                                                .contains( item.product()
                                                                               .id() ) )
                                   .map( CartItem::totalPrice )
                                   .reduce( BigDecimal.ZERO, BigDecimal::add );

        var appliedDiscount = switch ( discountCode.type() ) {
            case PERCENTAGE -> totalApplicable.multiply( discountCode.value()
                                                                     .divide( BigDecimal.valueOf( 100 ) ) );
            case AMOUNT -> discountCode.value();
        };

        var newTotal = totalBeforeDiscount().subtract( appliedDiscount );
        this.discountCode = discountCode;
        this.totalAfterDiscount = newTotal.compareTo( BigDecimal.ZERO ) < 0 ? BigDecimal.ZERO : newTotal;
    }
}
