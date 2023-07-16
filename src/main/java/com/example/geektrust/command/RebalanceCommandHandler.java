package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;


public class RebalanceCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    ) {
        rebalancePortfolioAndDisplayBalance(portfolio);
    }

    private void rebalancePortfolioAndDisplayBalance(Portfolio portfolio) {
        if (portfolio.isRebalancePossible()) {
            updatePortfolioAndDisplayBalanceAfterRebalance(portfolio);
        } else {
            System.out.println("CANNOT_REBALANCE");
        }
    }

    private void updatePortfolioAndDisplayBalanceAfterRebalance(Portfolio portfolio) {
        Investment updatedInvestment = new Investment();
        portfolio.rebalancePortfolio(updatedInvestment);
        portfolio.setTotalInvestmentAndAddToPortfolio(updatedInvestment);
        updatedInvestment.displayBalance();
    }
}
