package com.sequra.challenge.domain.payments.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "SQ_DISBURSEMENT")
@Entity
public class Disbursement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer week;
}
