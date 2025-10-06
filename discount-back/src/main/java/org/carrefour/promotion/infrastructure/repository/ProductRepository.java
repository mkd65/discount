package org.carrefour.promotion.infrastructure.repository;

import org.carrefour.promotion.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> { }
