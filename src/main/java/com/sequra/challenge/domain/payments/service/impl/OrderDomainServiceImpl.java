package com.sequra.challenge.domain.payments.service.impl;

import com.sequra.challenge.domain.payments.OrderRepository;
import com.sequra.challenge.domain.payments.service.OrderDomainService;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class OrderDomainServiceImpl implements OrderDomainService {

    private final OrderRepository orderRepository;
}
