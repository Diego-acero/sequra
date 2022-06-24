package com.sequra.challenge.domain.payments.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
@Table(name = "ORDER")
@Entity
public class Order {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "merchantId")
    private Merchant merchantId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopperId")
    private Shopper shopperId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    private ZonedDateTime completedAt;
}
