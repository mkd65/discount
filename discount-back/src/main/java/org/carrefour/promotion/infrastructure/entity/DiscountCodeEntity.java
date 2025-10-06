package org.carrefour.promotion.infrastructure.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "discount_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCodeEntity {

    @Id
    private String code;

    @Enumerated( EnumType.STRING)
    @Column(nullable = false)
    private DiscountType type;

    @Column(nullable = false)
    private BigDecimal value;

    private LocalDate validFrom;
    private LocalDate validTo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable( name = "discount_applicable_products", joinColumns = @JoinColumn(name = "discount_code"))
    @Column(name = "product_id")
    private Set<String> applicableProductIds;

    public enum DiscountType { PERCENTAGE, AMOUNT }

}
