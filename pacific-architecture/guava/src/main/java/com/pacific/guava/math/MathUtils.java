package com.pacific.guava.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public final class MathUtils {

    private static final int DEFAULT_SCALE = 4;
    private static final int DEFAULT_PRETTY_SCALE = 2;

    private MathUtils() {
    }

    public static final long[] LONG_POWERS_OF_TEN = new long[]{
            1L,
            10L,
            100L,
            1000L,
            10000L,
            100000L,
            1000000L,
            10000000L,
            100000000L,
            1000000000L,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L,
            1000000000000000000L,
    };

    /**
     * Constrains an int to a min and max.
     *
     * @param value Value to clamp.
     * @param min   The floor of the value.
     * @param max   The ceiling of the value.
     * @return The value constrained between the min and max.
     */
    public static int constrain(int value, int min, int max) {
        if (value > max) {
            return max;
        }

        if (value < min) {
            return min;
        }

        return value;
    }

    @NotNull
    public static float roundFloat(float value, int scale, int roundingMode) {
        return float2BigDecimal(value).setScale(scale, roundingMode).floatValue();
    }

    @NotNull
    public static double roundDouble(double value, int scale, int roundingMode) {
        return double2BigDecimal(value).setScale(scale, roundingMode).doubleValue();
    }

    @NotNull
    public static float roundDownFloat(float value) {
        return roundFloat(value, DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static double roundDownDouble(double value) {
        return roundDouble(value, DEFAULT_SCALE, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static float roundDownFloat(float value, int scale) {
        return roundFloat(value, scale, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static double roundDownDouble(double value, int scale) {
        return roundDouble(value, scale, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static String prettyFloat(float value, int scale, int roundingMode) {
        return float2BigDecimal(value).setScale(scale, roundingMode).toPlainString();
    }

    @NotNull
    public static String prettyDouble(double value, int scale, int roundingMode) {
        return double2BigDecimal(value).setScale(scale, roundingMode).toPlainString();
    }

    @NotNull
    public static String prettyFloat(float value) {
        return prettyFloat(value, DEFAULT_PRETTY_SCALE, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static String prettyDouble(double value) {
        return prettyDouble(value, DEFAULT_PRETTY_SCALE, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static String prettyFloat(float value, int scale) {
        return prettyFloat(value, scale, BigDecimal.ROUND_DOWN);
    }

    @NotNull
    public static String prettyDouble(double value, int scale) {
        return prettyDouble(value, scale, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal double2BigDecimal(double value) {
        return new BigDecimal(Double.toString(value));
    }

    public static BigDecimal float2BigDecimal(float value) {
        return new BigDecimal(Float.toString(value));
    }

    // C(n,m) n>m
    public static int combination(int n, int m) {
        return permutations(n, m) / permutations(m, m);
    }

    // A(n,m) n>m
    public static int permutations(int n, int m) {
        int result = 1;
        for (int i = 0; i < m; i++) {
            result *= (n - i);
        }
        return result;
    }

    // A(n,m) n>m
    public static int permutations2(int n, int m) {
        int result = 1;
        for (int i = m; i > 0; i--) {
            result *= n;
            n--;
        }
        return result;
    }

    public static double plus(double a, double b) {
        BigDecimal b1 = double2BigDecimal(a);
        BigDecimal b2 = double2BigDecimal(b);
        return b1.add(b2).doubleValue();
    }

    public static float plus(float a, float b) {
        BigDecimal b1 = float2BigDecimal(a);
        BigDecimal b2 = float2BigDecimal(b);
        return b1.add(b2).floatValue();
    }

    public static double subtract(double a, double b) {
        BigDecimal b1 = double2BigDecimal(a);
        BigDecimal b2 = double2BigDecimal(b);
        return b1.subtract(b2).doubleValue();
    }

    public static float subtract(float a, float b) {
        BigDecimal b1 = float2BigDecimal(a);
        BigDecimal b2 = float2BigDecimal(b);
        return b1.subtract(b2).floatValue();
    }

    public static double multiply(double... array) {
        return multiply(2, BigDecimal.ROUND_DOWN, array);
    }

    public static double multiply(int scale, double... array) {
        return multiply(scale, BigDecimal.ROUND_DOWN, array);
    }

    public static double multiply(int scale, int roundingMode, double... array) {
        BigDecimal result = new BigDecimal("1");
        for (double d : array) {
            result = result.multiply(double2BigDecimal(d));
        }
        return result.doubleValue();
    }

    public static float multiply(float... array) {
        return multiply(2, BigDecimal.ROUND_DOWN, array);
    }

    public static float multiply(int scale, float... array) {
        return multiply(scale, BigDecimal.ROUND_DOWN, array);
    }

    public static float multiply(int scale, int roundingMode, float... array) {
        BigDecimal result = new BigDecimal("1");
        for (float f : array) {
            result = result.multiply(float2BigDecimal(f));
        }
        return result.floatValue();
    }

    public static double divide(double a, double b) {
        return divide(a, b, 2, BigDecimal.ROUND_DOWN);
    }

    public static double divide(double a, double b, int scale) {
        return divide(a, b, scale, BigDecimal.ROUND_DOWN);
    }

    public static double divide(double a, double b, int scale, int roundingMode) {
        BigDecimal b1 = double2BigDecimal(a);
        BigDecimal b2 = double2BigDecimal(b);
        return b1.divide(b2, scale, roundingMode).doubleValue();
    }

    public static float divide(float a, float b) {
        return divide(a, b, 2, BigDecimal.ROUND_DOWN);
    }

    public static float divide(float a, float b, int scale) {
        return divide(a, b, scale, BigDecimal.ROUND_DOWN);
    }

    public static float divide(float a, float b, int scale, int roundingMode) {
        BigDecimal b1 = float2BigDecimal(a);
        BigDecimal b2 = float2BigDecimal(b);
        return b1.divide(b2, scale, roundingMode).floatValue();
    }
}