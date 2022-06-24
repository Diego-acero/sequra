package com.sequra.challenge.domain.payments.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantBasicData {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
