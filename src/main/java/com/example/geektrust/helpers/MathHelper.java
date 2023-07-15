package com.example.geektrust.helpers;

public class MathHelper {

    private static final Double PERCENTAGE_BASE = 100.0;

    private MathHelper() {
        throw new IllegalStateException("Helper Class");
    }

    public static double computePercentage(Double number, Double percentage) {
        return number * percentage / PERCENTAGE_BASE;
    }

    public static double computeFlooredPercentage(Double number, Double percentage) {
        return Math.floor(computePercentage(number, percentage));
    }

}
