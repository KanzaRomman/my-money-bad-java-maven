package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;


public class AllocateCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    ) {
        allocateFundsAndUpdatePortfolio(portfolio, instructionValues);
    }

    private void allocateFundsAndUpdatePortfolio(
            Portfolio portfolio,
            String[] funds
    ) {
        Investment investment = new Investment();
        investment.allocateFunds(funds);
        portfolio.updatePortfolioAndSetAllocatedPercentage(investment);
    }
}
