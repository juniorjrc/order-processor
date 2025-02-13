package com.juniorjrc.orderprocessor.publisher;

import com.juniorjrc.ordermodel.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.juniorjrc.ordermodel.constants.OrderMessageQueue.ORDER_NOTIFIER_QUEUE;
import static com.juniorjrc.ordermodel.constants.OrderMessageQueue.ORDER_SERVICE_EXCHANGE_NAME;

@Service
@AllArgsConstructor
public class OrderProcessorPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderToNotifier(final OrderDTO orderDTO) {
        this.rabbitTemplate.convertAndSend(
                ORDER_SERVICE_EXCHANGE_NAME,
                ORDER_NOTIFIER_QUEUE,
                orderDTO);
    }
}
