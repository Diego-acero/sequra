package com.sequra.challenge.domain.payments.entity;

import com.sequra.challenge.domain.payments.entity.common.ParticipantBasicData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "SQ_SHOPPER")
@Entity
public class Shopper {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_SQ_SHOPPER")
    @SequenceGenerator(name = "SEQ_SQ_SHOPPER", sequenceName = "SEQ_SQ_SHOPPER_ID", allocationSize = 1)
    private Long id;

    @Embedded
    private ParticipantBasicData basicData;

    @Column(nullable = false)
    private String nif;
}
