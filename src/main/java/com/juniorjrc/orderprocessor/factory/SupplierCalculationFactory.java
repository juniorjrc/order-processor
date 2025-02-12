package com.juniorjrc.orderprocessor.factory;

import com.juniorjrc.ordermodel.enums.SupplierEnum;
import com.juniorjrc.orderprocessor.strategy.AmbevProductCalculatorStrategy;
import com.juniorjrc.orderprocessor.strategy.CocaColaProductCalculatorStrategy;
import com.juniorjrc.orderprocessor.strategy.HeinekenProductCalculatorStrategy;
import com.juniorjrc.orderprocessor.strategy.SupplierProductCalculatorStrategy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import static com.juniorjrc.ordermodel.enums.SupplierEnum.AMBEV;
import static com.juniorjrc.ordermodel.enums.SupplierEnum.COCA_COLA;
import static com.juniorjrc.ordermodel.enums.SupplierEnum.HEINEKEN;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierCalculationFactory {

    private static final Map<SupplierEnum, SupplierProductCalculatorStrategy> STRATEGIES = new EnumMap<>(SupplierEnum.class);

    static {
        STRATEGIES.put(AMBEV, new AmbevProductCalculatorStrategy());
        STRATEGIES.put(COCA_COLA, new CocaColaProductCalculatorStrategy());
        STRATEGIES.put(HEINEKEN, new HeinekenProductCalculatorStrategy());
    }

    public static SupplierProductCalculatorStrategy getStrategy(SupplierEnum supplier) {
        return STRATEGIES.getOrDefault(supplier, productValue -> BigDecimal.ZERO);
    }
}
