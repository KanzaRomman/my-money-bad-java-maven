package com.example.geektrust.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathHelperTest {
    private static final double DELTA = 1e-15;

    @Test
    void computePercentage_shouldReturnCorrectPercentage() {
        double number = 1000.0;
        double percentage = 20.0;
        double expectedPercentage = 200.0;

        double computedPercentage = MathHelper.computePercentage(number, percentage);

        assertEquals(expectedPercentage, computedPercentage, DELTA);
    }

    @Test
    void computeFlooredPercentage_shouldReturnCorrectFlooredPercentage() {
        double number = 1000.0;
        double percentage = 20.5;
        double expectedFlooredPercentage = 205.0;

        double computedFlooredPercentage = MathHelper.computeFlooredPercentage(number, percentage);

        assertEquals(expectedFlooredPercentage, computedFlooredPercentage, DELTA);
    }
}
