package org.carrefour.promotion.infrastructure.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table( name = "cart" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntity {

    @Getter
    @Id
    private String id;

    @OneToMany( mappedBy = "cart",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER )
    private List<CartItemEntity> items;
    @ManyToOne
    DiscountCodeEntity discountCode;
    BigDecimal totalBeforeDiscount;
    BigDecimal totalAfterDiscount;

}
