package com.juniorjrc.orderprocessor.facade;

import com.juniorjrc.orderprocessor.service.OrderProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProcessorFacade {

    private final OrderProcessorService orderProcessorService;

    public void processOrder(final Long orderId) {
        this.orderProcessorService.processOrder(orderId);
    }
}
