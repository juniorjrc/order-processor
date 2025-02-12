package com.juniorjrc.orderprocessor.service;

import com.juniorjrc.ordermodel.dto.OrderDTO;
import com.juniorjrc.ordermodel.dto.UpdateOrderStatusRequestDTO;
import com.juniorjrc.orderprocessor.clients.OrderServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.juniorjrc.ordermodel.enums.OrderStatusEnum.ERROR;
import static com.juniorjrc.ordermodel.enums.OrderStatusEnum.PROCESSING;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProcessorService {

    private static final String ERROR_PROCESSING_THE_ORDER = "Error processing the order. Details: %s";

    private final OrderServiceClient orderServiceClient;

    public void processOrder(final Long orderId) {
        this.orderServiceClient.updateOrderStatus(orderId, new UpdateOrderStatusRequestDTO(PROCESSING, null));
        try {
            OrderDTO orderDTO = this.orderServiceClient.findById(orderId);
            log.info("{}", orderDTO.toString());
        } catch (Exception e) {
            this.orderServiceClient.updateOrderStatus(
                    orderId,
                    new UpdateOrderStatusRequestDTO(ERROR, String.format(ERROR_PROCESSING_THE_ORDER, e.getMessage())));
        }
    }
}
