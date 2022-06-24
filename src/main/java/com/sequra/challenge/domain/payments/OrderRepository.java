package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
