package com.juniorjrc.orderprocessor.clients;

import com.juniorjrc.ordermodel.dto.OrderDTO;
import com.juniorjrc.ordermodel.dto.UpdateOrderRequestDTO;
import com.juniorjrc.ordermodel.dto.UpdateOrderStatusRequestDTO;
import com.juniorjrc.orderprocessor.config.feign.BasicFeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@FeignClient(name = "order-service-client", url = "${api.clients.order-service.url}/order",
        configuration = BasicFeignClientConfiguration.class)
public interface OrderServiceClient {

    @GetMapping("/{orderId}")
    OrderDTO findById(@PathVariable final Long orderId);

    @PutMapping("/status/{orderId}")
    void updateOrderStatus(@PathVariable("orderId") final Long orderId,
                           @RequestBody UpdateOrderStatusRequestDTO updateOrderStatusRequestDTO);

    @PostMapping("/update-values")
    @ResponseStatus(HttpStatus.OK)
    void updateOrderValues(@RequestBody UpdateOrderRequestDTO updateOrderRequestDTO);
}
