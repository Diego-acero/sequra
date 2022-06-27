package com.sequra.challenge.port.adapter.calculatedisbursements;

import com.sequra.challenge.app.usecase.calculatedisbursements.CalculateDisbursementsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Singleton;

@RequiredArgsConstructor
@Slf4j
@Singleton
public class CalculateDisbursementSpringSchedulerAdapter implements CalculateDisbursementsPort {

    private final CalculateDisbursementsUseCase calculateDisbursementsUseCase;

    @Scheduled(cron = "${disbursement.cron}")
    public void doOnSchedule(){
        log.info("Starting scheduled task to calculate the disbursement");
        calculateDisbursements();
    }

    @Override
    public void calculateDisbursements() {
        calculateDisbursementsUseCase.calculateDisbursements();
    }


}
