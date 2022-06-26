package com.sequra.challenge.domain.payments.entity;

import com.sequra.challenge.domain.payments.entity.common.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
@Table(name = "SQ_ORDER")
@Entity
public class Order {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopperId")
    private Shopper shopper;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime completedAt;

    @Column(nullable = false)
    @Enumerated
    private OrderStatus status;
}
