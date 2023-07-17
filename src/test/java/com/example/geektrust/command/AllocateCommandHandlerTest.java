package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllocateCommandHandlerTest {
    private AllocateCommandHandler allocateCommandHandler;
    private Portfolio portfolio;

    @BeforeEach
    void setup() {
        allocateCommandHandler = new AllocateCommandHandler();
        portfolio = new Portfolio();
    }

    @Test
    void handleCommand_shouldAllocateFundsAndUpdatePortfolio() {
        String[] instructionValues = {"1000.0", "2000.0"};

        allocateCommandHandler.handleCommand(portfolio, instructionValues);

        assertEquals(1, portfolio.getPortfolioSize());
        Investment investment = portfolio.getLatestInvestment();
        assertEquals(2, investment.getInvestmentCount());
        assertEquals(1000.0, investment.getInvestmentValue(0));
        assertEquals(2000.0, investment.getInvestmentValue(1));
        assertEquals(3000.0, investment.getTotalInvestment());
        assertEquals(2, portfolio.getAllocatedPercentage().size());
    }

}