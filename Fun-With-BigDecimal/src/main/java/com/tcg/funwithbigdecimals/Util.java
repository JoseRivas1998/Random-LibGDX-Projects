package com.tcg.funwithbigdecimals;

import java.math.BigDecimal;
import java.math.MathContext;

public class Util {

    public static BigDecimal norm(BigDecimal number, BigDecimal min, BigDecimal max) {
        return number.subtract(min).divide(max.subtract(min), new MathContext(100));
    }

    public static BigDecimal map(BigDecimal number, BigDecimal min1, BigDecimal max1, BigDecimal min2, BigDecimal max2) {
        return min2.add(norm(number, min1, max1).multiply(max2.subtract(min2)));
    }

}
