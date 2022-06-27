package com.sequra.challenge.domain.payments.service;

import com.sequra.challenge.domain.payments.entity.Disbursement;

import java.util.List;

public interface PaymentsDomainService {
    List<Disbursement> calculateDisbursement();
}
