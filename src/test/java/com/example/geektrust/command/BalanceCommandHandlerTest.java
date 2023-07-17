package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BalanceCommandHandlerTest {

    private BalanceCommandHandler balanceCommandHandler;
    private Portfolio portfolio;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        balanceCommandHandler = new BalanceCommandHandler();
        portfolio = new Portfolio();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void handleCommand_shouldDisplayBalanceByMonth() {
        Investment initialAllocation = new Investment();
        initialAllocation.allocateFunds(new String[]{"1000.0"});
        portfolio.addToPortfolioAndRecordOperation(initialAllocation);

        Investment investmentPostSip = new Investment();
        investmentPostSip.addToInvestment(1500.0);
        portfolio.addToPortfolio(investmentPostSip);

        String[] instructionValues = {"JANUARY"};

        balanceCommandHandler.handleCommand(portfolio, instructionValues);

        String expectedOutput = "1500";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }
}