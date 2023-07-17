package com.example.geektrust.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {
    private Portfolio portfolio;
    private Investment investment1;
    private Investment investment2;

    @BeforeEach
    void setup() {
        portfolio = new Portfolio();
        investment1 = new Investment();
        investment2 = new Investment();
        investment1.addToInvestment(1000.0);
        investment2.addToInvestment(2000.0);
    }

    @Test
    void addToPortfolio_shouldAddInvestmentToPortfolio() {
        portfolio.addToPortfolio(investment1);
        assertEquals(1, portfolio.getPortfolioSize());
        assertSame(investment1, portfolio.getInvestmentByMonth(0));
    }

    @Test
    void setTotalInvestmentAndAddToPortfolio_shouldSetTotalInvestmentAndAddInvestmentToPortfolio() {
        portfolio.setTotalInvestmentAndAddToPortfolio(investment1);
        assertEquals(1, portfolio.getPortfolioSize());
        assertSame(investment1, portfolio.getInvestmentByMonth(0));
        assertEquals(1000.0, investment1.getTotalInvestment());
    }

    @Test
    void getLatestInvestment_shouldReturnLatestInvestment() {
        portfolio.addToPortfolioAndRecordOperation(investment1);
        portfolio.addToPortfolioAndRecordOperation(investment2);
        assertSame(investment2, portfolio.getLatestInvestment());
    }

    @Test
    void getPortfolioSize_shouldReturnPortfolioSize() {
        assertEquals(0, portfolio.getPortfolioSize());
        portfolio.addToPortfolio(investment1);
        assertEquals(1, portfolio.getPortfolioSize());
    }

    @Test
    void addSipToPortfolio_shouldAddSipToSystematicInvestmentPlan() {
        String[] amounts = {"1000.0", "2000.0"};
        portfolio.addSipToPortfolio(amounts);
        List<Double> sipAmounts = Arrays.asList(1000.0, 2000.0);
        assertEquals(sipAmounts.get(0), portfolio.getSystematicInvestmentPlanAmount(0));
        assertEquals(sipAmounts.get(1), portfolio.getSystematicInvestmentPlanAmount(1));
    }

    @Test
    void setAllocatedPercentage_shouldSetAllocatedPercentageBasedOnInitialInvestment() {
        investment1.addToInvestment(1000.0);
        investment1.setTotalInvestment();
        portfolio.addToPortfolio(investment1);
        portfolio.setAllocatedPercentage();
        List<Double> allocatedPercentage = Arrays.asList(0.5, 0.5);
        assertEquals(allocatedPercentage, portfolio.getAllocatedPercentage());
    }

    @Test
    void recordOperation_shouldIncrementOperationCount() {
        assertEquals(0, portfolio.getOperationCount());
        portfolio.recordOperation();
        assertEquals(1, portfolio.getOperationCount());
    }

    @Test
    void isFirstOperation_shouldReturnTrueForFirstOperation() {
        portfolio.recordOperation();
        assertTrue(portfolio.isFirstOperation());
    }

    @Test
    void getLatestInvestmentValue_shouldReturnCorrectValue() {
        portfolio.addToPortfolioAndRecordOperation(investment1);
        assertEquals(1000.0, portfolio.getLatestInvestmentValue(0));
    }

    @Test
    void isRebalancePossible_shouldReturnTrueWhenRebalanceIsPossible() {
        assertFalse(portfolio.isRebalancePossible());
        portfolio.addToPortfolio(investment1);
        assertTrue(portfolio.isRebalancePossible());
    }

    @Test
    void rebalancePortfolio_shouldRebalanceInvestmentValues() {
        investment1.addToInvestment(2000.0);
        investment1.setTotalInvestment();
        portfolio.addToPortfolioAndRecordOperation(investment1);

        investment2.addToInvestment(500.0);
        investment2.setTotalInvestment();
        portfolio.addToPortfolioAndRecordOperation(investment2);

        portfolio.setAllocatedPercentage();
        Investment updatedInvestment = new Investment();
        portfolio.rebalancePortfolio(updatedInvestment);
        assertEquals(833.3333333333333, updatedInvestment.getInvestmentValue(0));
        assertEquals(1666.6666666666665, updatedInvestment.getInvestmentValue(1));
    }
}
