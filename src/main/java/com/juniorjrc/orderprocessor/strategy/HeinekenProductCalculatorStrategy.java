package com.juniorjrc.orderprocessor.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.juniorjrc.ordermodel.enums.SupplierEnum.HEINEKEN;

@Service
public class HeinekenProductCalculatorStrategy implements SupplierProductCalculatorStrategy {

    @Override
    public BigDecimal calculateFinalProductValue(final BigDecimal productValue) {
        return productValue.subtract(productValue.multiply(HEINEKEN.getSupplierPercentage())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }
}
