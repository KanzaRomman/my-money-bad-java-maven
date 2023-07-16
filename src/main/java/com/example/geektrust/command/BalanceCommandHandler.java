package com.example.geektrust.command;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Month;


public class BalanceCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    ) {
        displayBalanceByMonthFromBalancePrintingInstruction(portfolio, instructionValues);
    }

    private void displayBalanceByMonthFromBalancePrintingInstruction(
            Portfolio portfolio,
            String[] balancePrintingInstructionValues
    ) {
        int monthIndex = getMonthIndexFromBalancePrintingInstructionValues(balancePrintingInstructionValues);
        Investment investmentByMonth = portfolio.getInvestmentByMonth(monthIndex);
        investmentByMonth.displayBalance();
    }

    private int getMonthIndexFromBalancePrintingInstructionValues(String[] balancePrintingInstructionValues) {
        final int MONTH_NAME_INDEX = 0;
        String monthName = balancePrintingInstructionValues[MONTH_NAME_INDEX];
        return Month.valueOf(monthName).getMonthNumber();
    }
}
