package com.sequra.challenge.domain.payments.service.impl;

import com.sequra.challenge.domain.payments.DisbursementsRepository;
import com.sequra.challenge.domain.payments.MerchantRepository;
import com.sequra.challenge.domain.payments.OrderRepository;
import com.sequra.challenge.domain.payments.entity.Disbursement;
import com.sequra.challenge.domain.payments.entity.Merchant;
import com.sequra.challenge.domain.payments.entity.Order;
import com.sequra.challenge.domain.payments.entity.common.OrderStatus;
import com.sequra.challenge.domain.payments.service.PaymentsDomainService;
import com.sequra.challenge.domain.payments.utils.PageFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Singleton
@RequiredArgsConstructor
public class PaymentsDomainServiceImpl implements PaymentsDomainService {

    private final OrderRepository orderRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public List<Disbursement> calculateDisbursement() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<Disbursement> disbursements = new LinkedList<>();
        merchants.forEach(merchant -> {
            Long totalOrders = orderRepository.countByMerchantIdAndStatus(merchant.getId(), OrderStatus.COMPLETED);
            PageFilter pageFilter = new PageFilter(20L, totalOrders);
            BigDecimal disbursementAmount = BigDecimal.ZERO;

            while(pageFilter.getPageNumber() < pageFilter.getTotalPages()) {
                Page<Order> order = orderRepository.findByMerchantIdAndStatus(
                        merchant.getId(),
                        OrderStatus.COMPLETED,
                        PageRequest.of(pageFilter.getPageNumber().intValue(), pageFilter.getResultsPerPage().intValue())
                );
                disbursementAmount = disbursementAmount.add(order.getContent().stream().map(Order::calculateDisbursement).reduce(BigDecimal.ZERO, BigDecimal::add));
                pageFilter.nextPage();
            }

            if (!disbursementAmount.equals(BigDecimal.ZERO)) {
                Disbursement disbursement = buildDisbursement(merchant, disbursementAmount);
                disbursements.add(disbursement);
            }

        });

        return disbursements;
    }

    private Disbursement buildDisbursement(Merchant merchant, BigDecimal disbursementAmount) {
        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(disbursementAmount);
        disbursement.setMerchant(merchant);
        disbursement.calculateYearAndWeek();
        return disbursement;
    }
}
