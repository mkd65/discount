package org.carrefour.promotion.infrastructure.repository;

import org.carrefour.promotion.infrastructure.entity.DiscountCodeEntity;
import org.carrefour.promotion.domain.model.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountCodeRepository extends JpaRepository<DiscountCodeEntity, String> {
    Optional<DiscountCodeEntity> findByCode( String code );
}
