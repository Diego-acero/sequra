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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_SQ_ORDER")
    @SequenceGenerator(name = "SEQ_SQ_ORDER", sequenceName = "SEQ_SQ_ORDER_ID", allocationSize = 1)
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

    public BigDecimal calculateDisbursement() {
        BigDecimal oneHundred = BigDecimal.valueOf(100L);

        if(this.amount.longValue() < 50L) {
            return this.amount.multiply(BigDecimal.ONE).divide(oneHundred);
        } else if (this.amount.longValue() < 300L) {
            return this.amount.multiply(BigDecimal.valueOf(0.95)).divide(oneHundred);
        } else {
            return this.amount.multiply(BigDecimal.valueOf(0.85)).divide(oneHundred);
        }
    }
}
