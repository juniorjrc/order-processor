package com.juniorjrc.orderprocessor.service;

import com.juniorjrc.ordermodel.dto.OrderDTO;
import com.juniorjrc.ordermodel.dto.ProductDTO;
import com.juniorjrc.ordermodel.dto.UpdateOrderRequestDTO;
import com.juniorjrc.ordermodel.dto.UpdateOrderStatusRequestDTO;
import com.juniorjrc.ordermodel.enums.SupplierEnum;
import com.juniorjrc.orderprocessor.clients.OrderServiceClient;
import com.juniorjrc.orderprocessor.factory.SupplierCalculationFactory;
import com.juniorjrc.orderprocessor.strategy.SupplierProductCalculatorStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.juniorjrc.ordermodel.enums.OrderStatusEnum.ERROR;
import static com.juniorjrc.ordermodel.enums.OrderStatusEnum.PROCESSED;
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
            orderDTO = calculateOrderValues(orderDTO);
            this.orderServiceClient.updateOrderValues(
                    new UpdateOrderRequestDTO(
                            orderDTO.id(),
                            orderDTO.orderValue(),
                            orderDTO.orderFinalValue(),
                            PROCESSED));
            //TO DO notify product B
        } catch (Exception e) {
            this.orderServiceClient.updateOrderStatus(
                    orderId,
                    new UpdateOrderStatusRequestDTO(ERROR, String.format(ERROR_PROCESSING_THE_ORDER, e.getMessage())));
        }
    }

    public OrderDTO calculateOrderValues(final OrderDTO orderDTO) {
        BigDecimal orderValue = orderDTO.products()
                .stream()
                .map(ProductDTO::productValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal orderFinalValue = orderDTO.products()
                .stream()
                .map(product -> {
                    SupplierEnum supplierEnum = SupplierEnum.valueOf(product.supplierName().toUpperCase());
                    SupplierProductCalculatorStrategy calculator = SupplierCalculationFactory.getStrategy(supplierEnum);
                    return calculator.calculateFinalProductValue(product.productValue());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderDTO(
                orderDTO.id(),
                orderDTO.customerName(),
                orderValue,
                orderFinalValue,
                orderDTO.status(),
                orderDTO.errorDetails(),
                orderDTO.products()
        );
    }
}
