package com.sequra.challenge.domain.payments.entity;

import com.sequra.challenge.domain.payments.entity.common.ParticipantBasicData;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "SHOPPER")
@Entity
public class Shopper {

    @Id
    private Long id;

    @Embedded
    private ParticipantBasicData basicData;

    @Column(nullable = false)
    private String nif;
}
