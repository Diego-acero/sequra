package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopperRepository extends JpaRepository<Shopper, Long> {
}
