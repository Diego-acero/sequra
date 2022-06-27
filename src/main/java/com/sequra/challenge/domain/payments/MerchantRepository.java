package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
