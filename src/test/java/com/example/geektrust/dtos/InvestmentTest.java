package com.example.geektrust.dtos;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentTest {
    @Test
    void addToInvestment_shouldAddToInvestmentList() {
        Investment investment = new Investment();
        investment.addToInvestment(1000.0);
        investment.addToInvestment(2000.0);

        assertEquals(2, investment.getInvestmentCount());
        assertEquals(1000.0, investment.getInvestmentValue(0));
        assertEquals(2000.0, investment.getInvestmentValue(1));
    }

    @Test
    void allocateFunds_shouldAddToInvestmentListAndSetTotalInvestment() {
        Investment investment = new Investment();
        String[] funds = {"1000.0", "2000.0"};
        investment.allocateFunds(funds);

        assertEquals(2, investment.getInvestmentCount());
        assertEquals(1000.0, investment.getInvestmentValue(0));
        assertEquals(2000.0, investment.getInvestmentValue(1));
        assertEquals(3000.0, investment.getTotalInvestment());
    }

    @Test
    void getInvestmentPercentage_shouldReturnCorrectInvestmentPercentage() {
        Investment investment = new Investment();
        String[] funds = {"1000.0", "2000.0"};
        investment.allocateFunds(funds);

        assertEquals(0.3333333333333333, investment.getInvestmentPercentage(0));
        assertEquals(0.6666666666666666, investment.getInvestmentPercentage(1));
    }

    @Test
    void updateInvestmentAfterMarketChange_shouldUpdateInvestmentBasedOnMarketChange() {
        Investment investment = new Investment();
        String[] funds = {"1000.0", "2000.0"};
        investment.allocateFunds(funds);

        Portfolio portfolio = new Portfolio();
        portfolio.addToPortfolioAndRecordOperation(investment);
        List<Double> marketChangeValues = new ArrayList<>();
        marketChangeValues.add(0.2);
        marketChangeValues.add(-0.1);

        Investment investmentAfterMarketChange = new Investment();
        investmentAfterMarketChange.updateInvestmentAfterMarketChange(portfolio, marketChangeValues);

        assertEquals(1002.0, investmentAfterMarketChange.getInvestmentValue(0));
        assertEquals(1998.0, investmentAfterMarketChange.getInvestmentValue(1));
        assertEquals(3000.0, investmentAfterMarketChange.getTotalInvestment());
    }

    @Test
    void buildBalanceString_shouldReturnCorrectBalanceString() {
        Investment investment = new Investment();
        investment.addToInvestment(1000.0);
        investment.addToInvestment(2000.0);

        String expectedBalanceString = "1000 2000 ";

        String balanceString = investment.buildBalanceString();

        assertEquals(expectedBalanceString, balanceString);
    }
}
