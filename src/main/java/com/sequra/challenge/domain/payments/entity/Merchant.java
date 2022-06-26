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
    private Long id;

    @Embedded
    private ParticipantBasicData participantBasicData;

    @Column(nullable = false)
    private String cif;
}
