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
                        addSipToPortfolio(portfolio, instructionValues);
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
        allocateFunds(investment, funds);
        updatePortfolioAndSetAllocatedPercentage(portfolio, investment);
    }

    private static void allocateFunds(
            Investment investment,
            String[] funds
    ) {
        for (String fund : funds) {
            investment.addToInvestment(
                    Double.parseDouble(fund)
            );
        }
        investment.setTotalInvestment();
    }

    private static void updatePortfolioAndSetAllocatedPercentage(
            Portfolio portfolio,
            Investment investment
    ) {
        addToPortfolioAndRecordOperation(portfolio, investment);
        portfolio.setAllocatedPercentage();
    }

    private static void addSipToPortfolio(
            Portfolio portfolio,
            String[] amounts
    ) {
        for (String amount : amounts) {
            portfolio.addToSystematicInvestmentPlan(Double.parseDouble(amount));
        }
    }

    public static void updateInvestmentAndPortfolioAsPerMarketChange(
            Portfolio portfolio,
            String[] marketChangeInstructionValues
    ) {
        List<Double> marketChangeValues = extractNumericValuesFromInstruction(marketChangeInstructionValues);
        Investment investmentAfterMarketChange = new Investment();

        updateInvestmentAfterMarketChange(portfolio, marketChangeValues, investmentAfterMarketChange);
        addToPortfolioAndRecordOperation(portfolio, investmentAfterMarketChange);
    }

    private static void updateInvestmentAfterMarketChange(
            Portfolio portfolio,
            List<Double> marketChangeValues,
            Investment investmentAfterMarketChange
    ) {

        for (int i = 0; i < marketChangeValues.size(); i++) {
            double latestInvestmentValue = getLatestInvestmentValue(portfolio, i);
            double marketChangeValue = marketChangeValues.get(i);

            double investmentValueAfterMarketChange = computeInvestmentValueAfterMarketChange(
                    latestInvestmentValue,
                    marketChangeValue
            );
            investmentAfterMarketChange.addToInvestment(investmentValueAfterMarketChange);
        }

        investmentAfterMarketChange.setTotalInvestment();
    }

    public static double getLatestInvestmentValue(
            Portfolio portfolio,
            int index
    ) {
        Investment latestInvestment = portfolio.getLatestInvestment();
        double latestInvestmentValue;

        if (portfolio.isFirstOperation()) {
            latestInvestmentValue = latestInvestment.getInvestmentValue(index);
        } else {
            latestInvestmentValue = latestInvestment.getInvestmentValue(index) +
                    portfolio.getSystematicInvestmentPlanAmount(index);
        }

        return latestInvestmentValue;
    }

    public static double computeInvestmentValueAfterMarketChange(
            Double latestInvestmentValue,
            Double marketChangeValue
    ) {
        double percentChangeInInvestment = computeFlooredPercentage(latestInvestmentValue, marketChangeValue);
        return percentChangeInInvestment + latestInvestmentValue;
    }

    private static void addToPortfolioAndRecordOperation(
            Portfolio portfolio,
            Investment investmentAfterMarketChange
    ) {
        portfolio.addToPortfolio(investmentAfterMarketChange);
        portfolio.recordOperation();
    }

    public static void displayBalanceByMonthFromBalancePrintingInstruction(
            Portfolio portfolio,
            String[] balancePrintingInstructionValues
    ) {
        int monthIndex = getMonthIndexFromBalancePrintingInstructionValues(balancePrintingInstructionValues);
        Investment investmentByMonth = portfolio.getInvestmentByMonth(monthIndex + 1);
        displayBalance(investmentByMonth);
    }

    public static int getMonthIndexFromBalancePrintingInstructionValues(String[] balancePrintingInstructionValues) {
        final int MONTH_NAME_INDEX = 0;
        final int OFFSET = 1;

        String monthName = balancePrintingInstructionValues[MONTH_NAME_INDEX];
        int monthNumber = Month.valueOf(monthName).getMonthNumber();
        return monthNumber - OFFSET;
    }

    private static void displayBalance(Investment investmentByMonth) {
        String balanceString = buildBalanceString(investmentByMonth);
        System.out.println(balanceString);
    }

    public static String buildBalanceString(Investment investment) {
        StringBuilder stringBuilder = new StringBuilder();
        String separator = " ";

        for (int i = 0; i < investment.getInvestmentCount(); i++) {
            stringBuilder.append(investment.getInvestmentValue(i).shortValue());
            stringBuilder.append(separator);
        }

        return stringBuilder.toString();
    }

    public static void rebalancePortfolioAndDisplayBalance(Portfolio portfolio) {
        if (isRebalancePossible(portfolio)) {
            updatePortfolioAndDisplayBalanceAfterRebalance(portfolio);
        } else {
            System.out.println("CANNOT_REBALANCE");
        }
    }

    public static boolean isRebalancePossible(Portfolio portfolio) {
        final int OFFSET = 1;
        final int REBALANCE_INTERVAL = 6;
        final int ZERO = 0;

        return (portfolio.getPortfolioSize() - OFFSET) % REBALANCE_INTERVAL == ZERO;
    }

    private static void updatePortfolioAndDisplayBalanceAfterRebalance(Portfolio portfolio) {
        Investment updatedInvestment = new Investment();
        rebalancePortfolio(portfolio, updatedInvestment);
        setTotalInvestmentAndAddToPortfolio(portfolio, updatedInvestment);
        displayBalance(updatedInvestment);
    }

    private static void rebalancePortfolio(
            Portfolio portfolio,
            Investment updatedInvestment
    ) {
        Investment latestInvestment = portfolio.getLatestInvestment();
        double latestTotalInvestment = latestInvestment.getTotalInvestment();
        List<Double> portfolioAllocatedPercentages = portfolio.getAllocatedPercentage();

        for (double allocatedPercentage : portfolioAllocatedPercentages) {
            updatedInvestment.addToInvestment(allocatedPercentage * latestTotalInvestment);
        }
    }

    private static void setTotalInvestmentAndAddToPortfolio(
            Portfolio portfolio,
            Investment updatedInvestment
    ) {
        updatedInvestment.setTotalInvestment();
        portfolio.addToPortfolio(updatedInvestment);
    }

    public enum Command {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

}
