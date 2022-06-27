package com.sequra.challenge.domain.payments;

import com.sequra.challenge.domain.payments.entity.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisbursementsRepository extends JpaRepository<Disbursement, Long> {
}
