package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RebalanceCommandHandlerTest {
    private RebalanceCommandHandler rebalanceCommandHandler;
    private Portfolio portfolio;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        rebalanceCommandHandler = new RebalanceCommandHandler();
        portfolio = new Portfolio();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void handleCommand_whenRebalanceIsPossible_shouldUpdatePortfolioAndDisplayBalance() {
        Investment initialInvestment = new Investment();
        initialInvestment.addToInvestment(1000.0);
        initialInvestment.addToInvestment(2000.0);
        initialInvestment.setTotalInvestment();
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.setAllocatedPercentage();
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);

        String[] instructionValues = {};

        rebalanceCommandHandler.handleCommand(portfolio, instructionValues);

        String expectedOutput = "1000 2000";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void handleCommand_whenRebalanceIsNotPossible_shouldPrintCannotRebalance() {
        Investment initialInvestment = new Investment();
        initialInvestment.addToInvestment(1000.0);
        initialInvestment.setTotalInvestment();
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);
        portfolio.addToPortfolioAndRecordOperation(initialInvestment);

        String[] instructionValues = {};

        rebalanceCommandHandler.handleCommand(portfolio, instructionValues);

        String expectedOutput = "CANNOT_REBALANCE";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }
}