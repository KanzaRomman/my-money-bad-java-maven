package com.example.geektrust;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testCreateMonths() {
        List<String> months = Main.createMonths();
        assertNotNull(months);
    }

    @Test
    public void changeGains() {
        Map<Integer, List<Double>> portfolio = new HashMap<>();
        List<Double> listValues = new ArrayList<>();
        listValues.add(6000.0);
        listValues.add(3000.0);
        listValues.add(1000.0);
        listValues.add(10000.0);
        portfolio.put(0, listValues);

        List<Double> sip = new ArrayList<>();
        sip.add(2000.0);
        sip.add(1000.0);
        sip.add(500.0);

        String[] instructions = {
                "CHANGE",
                "4.00%",
                "10.00%",
                "2.00%",
                "JANUARY"
        };

        int count = 1;

        int updatedCount = Main.changeGains(portfolio, sip, instructions, count);

        // Verify the updated portfolio and count
        assertEquals(2, updatedCount);

        List<Double> updatedInvestment = portfolio.get(1);

        assertEquals(4, updatedInvestment.size());
        assertEquals(6240.0, updatedInvestment.get(0));
        assertEquals(3300.0, updatedInvestment.get(1));
        assertEquals(1020.0, updatedInvestment.get(2));
        assertEquals(10560.0, updatedInvestment.get(3));
    }

    @Test
    public void calculatePercent() {
        List<Double> investment = Arrays.asList(1.3,4.5,2.4,8.9);
        Main.calculatePercent(investment,100.9);
    }

    @Test
    public void printBalance() {
        List<Double> investment = Arrays.asList(1.3,4.5,2.4,8.9);
        Map<Integer, List<Double>> portfolio = new HashMap<>();
        portfolio.put(1, investment);
        String balance = Main.printBalance(portfolio,0);
        assertEquals("1 4 2 ", balance);
    }
}