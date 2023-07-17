package com.example.geektrust.command;

import com.example.geektrust.dtos.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SipCommandHandlerTest {

    private SipCommandHandler sipCommandHandler;
    private Portfolio portfolio;

    @BeforeEach
    void setup() {
        sipCommandHandler = new SipCommandHandler();
        portfolio = new Portfolio();
    }

    @Test
    void handleCommand_shouldAddSipToPortfolio() {
        String[] instructionValues = {"1000.0", "2000.0", "3000.0"};

        sipCommandHandler.handleCommand(portfolio, instructionValues);

        List<Double> expectedSipValues = Arrays.asList(1000.0, 2000.0, 3000.0);
        assertEquals(expectedSipValues.get(0), portfolio.getSystematicInvestmentPlanAmount(0));
        assertEquals(expectedSipValues.get(1), portfolio.getSystematicInvestmentPlanAmount(1));
        assertEquals(expectedSipValues.get(2), portfolio.getSystematicInvestmentPlanAmount(2));
    }

}