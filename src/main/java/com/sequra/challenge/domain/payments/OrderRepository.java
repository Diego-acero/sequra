package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Order;
import com.sequra.challenge.domain.payments.entity.common.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByMerchantIdAndStatus(Long merchantId, OrderStatus status, Pageable pageable);

    Long countByMerchantIdAndStatus(Long merchantId, OrderStatus status);
}
