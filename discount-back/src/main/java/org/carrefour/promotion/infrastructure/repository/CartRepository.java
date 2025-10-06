package org.carrefour.promotion.infrastructure.repository;

import org.carrefour.promotion.infrastructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, String> { }
