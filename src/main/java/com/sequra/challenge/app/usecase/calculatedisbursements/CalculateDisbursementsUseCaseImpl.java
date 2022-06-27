package com.sequra.challenge.app.usecase.calculatedisbursements;

import com.sequra.challenge.domain.payments.DisbursementsRepository;
import com.sequra.challenge.domain.payments.entity.Disbursement;
import com.sequra.challenge.domain.payments.service.PaymentsDomainService;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class CalculateDisbursementsUseCaseImpl implements CalculateDisbursementsUseCase {

    private final PaymentsDomainService paymentsDomainService;
    private final DisbursementsRepository disbursementsRepository;

    @Override
    @Transactional
    public void calculateDisbursements() {
        List<Disbursement> disbursements = paymentsDomainService.calculateDisbursement();
        disbursementsRepository.saveAll(disbursements);
    }
}
