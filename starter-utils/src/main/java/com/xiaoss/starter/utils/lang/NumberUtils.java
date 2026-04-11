package com.xiaoss.starter.utils.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumberUtils {

    private NumberUtils() {
    }

    public static BigDecimal scale(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }
}
