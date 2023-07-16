package com.example.geektrust;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Month;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.example.geektrust.helpers.FileInstructionHelper.extractCommandFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.extractNumericValuesFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.extractValuesFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.getInstructionFromFileLine;
import static com.example.geektrust.helpers.FileInstructionHelper.getTrimmedLinesAsList;
import static com.example.geektrust.helpers.FileInstructionHelper.readLinesFromFile;
import static com.example.geektrust.helpers.MathHelper.computeFlooredPercentage;

public class Main {

    public static void main(String[] args) {

        Path filePath = new File(args[0]).toPath();
        try (Stream<String> rawFileLines = readLinesFromFile(filePath)) {
            List<String> fileLines = getTrimmedLinesAsList(rawFileLines);

            Portfolio portfolio = new Portfolio();

            for (String fileLine : fileLines) {
                String[] instruction = getInstructionFromFileLine(fileLine);
                Command command = extractCommandFromInstruction(instruction);
                String[] instructionValues = extractValuesFromInstruction(instruction);

                switch (command) {
                    case ALLOCATE:
                        allocateFundsAndUpdatePortfolio(portfolio, instructionValues);
                        break;
                    case SIP:
                        portfolio.addSipToPortfolio(instructionValues);
                        break;
                    case CHANGE:
                        updateInvestmentAndPortfolioAsPerMarketChange(portfolio, instructionValues);
                        break;
                    case BALANCE:
                        displayBalanceByMonthFromBalancePrintingInstruction(portfolio, instructionValues);
                        break;
                    case REBALANCE:
                        rebalancePortfolioAndDisplayBalance(portfolio);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void allocateFundsAndUpdatePortfolio(
            Portfolio portfolio,
            String[] funds
    ) {
        Investment investment = new Investment();
        investment.allocateFunds(funds);
        portfolio.updatePortfolioAndSetAllocatedPercentage(investment);
    }

    public static void updateInvestmentAndPortfolioAsPerMarketChange(
            Portfolio portfolio,
            String[] marketChangeInstructionValues
    ) {
        List<Double> marketChangeValues = extractNumericValuesFromInstruction(marketChangeInstructionValues);
        Investment investmentAfterMarketChange = new Investment();

        investmentAfterMarketChange.updateInvestmentAfterMarketChange(portfolio, marketChangeValues);
        portfolio.addToPortfolioAndRecordOperation(investmentAfterMarketChange);
    }

    public static void displayBalanceByMonthFromBalancePrintingInstruction(
            Portfolio portfolio,
            String[] balancePrintingInstructionValues
    ) {
        int monthIndex = getMonthIndexFromBalancePrintingInstructionValues(balancePrintingInstructionValues);
        Investment investmentByMonth = portfolio.getInvestmentByMonth(monthIndex);
        investmentByMonth.displayBalance();
    }

    public static int getMonthIndexFromBalancePrintingInstructionValues(String[] balancePrintingInstructionValues) {
        final int MONTH_NAME_INDEX = 0;
        String monthName = balancePrintingInstructionValues[MONTH_NAME_INDEX];
        return Month.valueOf(monthName).getMonthNumber();
    }

    public static void rebalancePortfolioAndDisplayBalance(Portfolio portfolio) {
        if (portfolio.isRebalancePossible()) {
            updatePortfolioAndDisplayBalanceAfterRebalance(portfolio);
        } else {
            System.out.println("CANNOT_REBALANCE");
        }
    }

    private static void updatePortfolioAndDisplayBalanceAfterRebalance(Portfolio portfolio) {
        Investment updatedInvestment = new Investment();
        portfolio.rebalancePortfolio(updatedInvestment);
        portfolio.setTotalInvestmentAndAddToPortfolio(updatedInvestment);
        updatedInvestment.displayBalance();
    }

    public enum Command {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

}
