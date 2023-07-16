package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;

import java.util.List;

import static com.example.geektrust.helpers.FileInstructionHelper.extractNumericValuesFromInstruction;


public class ChangeCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    ) {
        updateInvestmentAndPortfolioAsPerMarketChange(portfolio, instructionValues);
    }

    private void updateInvestmentAndPortfolioAsPerMarketChange(
            Portfolio portfolio,
            String[] marketChangeInstructionValues
    ) {
        List<Double> marketChangeValues = extractNumericValuesFromInstruction(marketChangeInstructionValues);
        Investment investmentAfterMarketChange = new Investment();

        investmentAfterMarketChange.updateInvestmentAfterMarketChange(portfolio, marketChangeValues);
        portfolio.addToPortfolioAndRecordOperation(investmentAfterMarketChange);
    }
}
