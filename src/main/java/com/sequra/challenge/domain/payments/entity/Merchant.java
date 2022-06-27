package com.sequra.challenge.domain.payments.entity;

import com.sequra.challenge.domain.payments.entity.common.ParticipantBasicData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "SQ_MERCHANT")
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_SQ_MERCHANT")
    @SequenceGenerator(name = "SEQ_SQ_MERCHANT", sequenceName = "SEQ_SQ_MERCHANT_ID", allocationSize = 1)
    private Long id;

    @Embedded
    private ParticipantBasicData participantBasicData;

    @Column(nullable = false)
    private String cif;
}
