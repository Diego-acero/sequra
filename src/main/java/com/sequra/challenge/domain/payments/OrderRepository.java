package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Order;
import com.sequra.challenge.domain.payments.entity.common.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o WHERE o.merchant.id = :merchantId AND o.status = :status AND o.completedAt BETWEEN :lastWeek AND :today")
    Page<Order> findByMerchantIdAndStatusAndCompletedLastWeek(
            @Param("merchantId") Long merchantId,
            @Param("status") OrderStatus status,
            @Param("today") ZonedDateTime today,
            @Param("lastWeek") ZonedDateTime lastWeek,
            Pageable pageable
    );

    @Query(value = "SELECT count(o) FROM Order o WHERE o.merchant.id = :merchantId AND o.status = :status AND o.completedAt BETWEEN :lastWeek AND :today")
    Long countByMerchantIdAndStatusAndCompletedLastWeek(
            @Param("merchantId") Long merchantId,
            @Param("status") OrderStatus status,
            @Param("today") ZonedDateTime today,
            @Param("lastWeek") ZonedDateTime lastWeek
    );
}
