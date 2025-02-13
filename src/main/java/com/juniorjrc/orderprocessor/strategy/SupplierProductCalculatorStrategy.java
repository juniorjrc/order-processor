package com.juniorjrc.orderprocessor.strategy;

import java.math.BigDecimal;

public interface SupplierProductCalculatorStrategy {
    BigDecimal calculateFinalProductValue(BigDecimal productValue);
}
