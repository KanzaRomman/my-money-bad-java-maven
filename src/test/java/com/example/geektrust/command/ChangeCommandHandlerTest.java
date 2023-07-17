package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChangeCommandHandlerTest {
    private ChangeCommandHandler changeCommandHandler;
    private Portfolio portfolio;

    @BeforeEach
    void setup() {
        changeCommandHandler = new ChangeCommandHandler();
        portfolio = new Portfolio();
    }

    @Test
    void handleCommand_shouldUpdateInvestmentAndPortfolioAsPerMarketChange() {
        Investment initialInvestment = new Investment();
        initialInvestment.addToInvestment(1000.0);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);

        String[] instructionValues = {"10.0"};

        changeCommandHandler.handleCommand(portfolio, instructionValues);

        Investment updatedInvestment = portfolio.getLatestInvestment();
        List<Double> expectedInvestmentValues = Arrays.asList(1100.0, 1220.0, 1590.0);
        assertEquals(1, updatedInvestment.getInvestmentCount());
        assertEquals(1100.0, updatedInvestment.getTotalInvestment());
        assertEquals(2, portfolio.getOperationCount());
    }
}