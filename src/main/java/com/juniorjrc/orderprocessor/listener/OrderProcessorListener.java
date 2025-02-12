package com.juniorjrc.orderprocessor.listener;

import com.juniorjrc.orderprocessor.facade.OrderProcessorFacade;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.juniorjrc.ordermodel.constants.OrderMessageQueue.ORDER_PROCESSOR_QUEUE;

@Component
@AllArgsConstructor
public class OrderProcessorListener {

    private final OrderProcessorFacade orderProcessorFacade;

    @RabbitListener(queues = ORDER_PROCESSOR_QUEUE, autoStartup = "true", concurrency = "3")
    public void processOrder(final String orderId) {
        this.orderProcessorFacade.processOrder(Long.valueOf(orderId));
    }
}
