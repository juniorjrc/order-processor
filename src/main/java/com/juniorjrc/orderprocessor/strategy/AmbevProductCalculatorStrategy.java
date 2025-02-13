package com.juniorjrc.orderprocessor.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.juniorjrc.ordermodel.enums.SupplierEnum.AMBEV;

@Service
public class AmbevProductCalculatorStrategy implements SupplierProductCalculatorStrategy {

    @Override
    public BigDecimal calculateFinalProductValue(final BigDecimal productValue) {
        return productValue.subtract(productValue.multiply(AMBEV.getSupplierPercentage())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }
}
