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
import static com.example.geektrust.helpers.FileInstructionHelper.getInstructionFromFileLine;
import static com.example.geektrust.helpers.FileInstructionHelper.getTrimmedLinesAsList;
import static com.example.geektrust.helpers.FileInstructionHelper.getValuesFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.readLinesFromFile;
import static com.example.geektrust.helpers.MathHelper.computeFlooredPercentage;

public class Main {

    public enum Command  {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

    public static void main(String[] args) {

        Path filePath = new File(args[0]).toPath();
        try (Stream<String> rawFileLines = readLinesFromFile(filePath)) {
            List<String> fileLines = getTrimmedLinesAsList(rawFileLines);

            Portfolio portfolio = new Portfolio();
            Investment updatedInvestment = new Investment();
            Investment investment = new Investment();

            for (String fileLine : fileLines) {
                String[] instruction = getInstructionFromFileLine(fileLine);
                Command command = extractCommandFromInstruction(instruction);

                switch (command) {
                    case ALLOCATE:
                        allocateFundsAndUpdatePortfolio(portfolio, investment, instruction);
                        break;
                    case SIP:
                        addSipToPortfolio(portfolio, instruction);
                        break;
                    case CHANGE:
                        updateInvestmentAndPortfolioAsPerMarketChange(portfolio, instruction);
                        break;
                    case BALANCE:
                        printBalance(portfolio, instruction);
                        break;
                    case REBALANCE:
                        int size = portfolio.getPortfolioSize() - 1;
                        if (size % 6 == 0) {
                            printRebalance(portfolio, updatedInvestment);
                        } else {
                            System.out.println("CANNOT_REBALANCE");
                        }
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addSipToPortfolio(Portfolio portfolio, String[] instruction) {
        String[] amounts = getValuesFromInstruction(instruction);
        for (String amount : amounts) {
            portfolio.addToSystematicInvestmentPlan(Double.parseDouble(amount));
        }
    }

    public static void allocateFundsAndUpdatePortfolio(
            Portfolio portfolio,
            Investment investment,
            String[] instruction
    ) {
        String[] funds = getValuesFromInstruction(instruction);
        allocateFunds(investment, funds);
        updatePortfolioAndSetAllocatedPercentage(portfolio, investment);
    }

    private static void allocateFunds(Investment investment, String[] funds) {

        for (String fund : funds) {
            investment.addToInvestment(
                    Double.parseDouble(fund)
            );
        }

        investment.setTotalInvestment();
    }

    private static void updatePortfolioAndSetAllocatedPercentage(Portfolio portfolio, Investment investment) {
        addToPortfolioAndRecordOperation(portfolio, investment);
        portfolio.setAllocatedPercentage();
    }

    public static void updateInvestmentAndPortfolioAsPerMarketChange(Portfolio portfolio, String[] marketChangeValueInstruction) {
        List<Double> marketChangeValues = extractNumericValuesFromInstruction(marketChangeValueInstruction);
        Investment investmentAfterMarketChange = new Investment();

        updateInvestmentAfterMarketChange(portfolio, marketChangeValues, investmentAfterMarketChange);
        addToPortfolioAndRecordOperation(portfolio, investmentAfterMarketChange);
    }

    private static void updateInvestmentAfterMarketChange(Portfolio portfolio, List<Double> marketChangeValues, Investment investmentAfterMarketChange) {

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

    public static double getLatestInvestmentValue(Portfolio portfolio, int index) {
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

    public static double computeInvestmentValueAfterMarketChange(Double latestInvestmentValue, Double marketChangeValue) {
        double percentChangeInInvestment = computeFlooredPercentage(latestInvestmentValue, marketChangeValue);
        return percentChangeInInvestment + latestInvestmentValue;
    }

    private static void addToPortfolioAndRecordOperation(Portfolio portfolio, Investment investmentAfterMarketChange) {
        portfolio.addToPortfolio(investmentAfterMarketChange);
        portfolio.recordOperation();
    }

    public static String printBalance(Portfolio portfolio, String[] instruction) {
        int index = Month.valueOf(getValuesFromInstruction(instruction)[0]).getMonthNumber() - 1;
        Investment monthlyValues = portfolio.getInvestmentByMonth(index + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < monthlyValues.getInvestmentCount(); i++) {
            sb.append(monthlyValues.getInvestmentValue(i).shortValue());
            sb.append(" ");
        }
        System.out.println(sb);
        return sb.toString();
    }

    public static void printRebalance(Portfolio portfolio, Investment updatedInvestment) {
        double total;
        Investment listValues;
        Double printValue;
        StringBuilder sb = new StringBuilder();

        listValues = portfolio.getInvestmentByMonth(portfolio.getOperationCount() - 1);

        for (double d : portfolio.getAllocatedPercentage()) {
            updatedInvestment.addToInvestment(d * listValues.getTotalInvestment());
            printValue = d * listValues.getTotalInvestment();
            sb.append(printValue.shortValue());
            sb.append(" ");
        }

        updatedInvestment.setTotalInvestment();

        portfolio.addToPortfolio(updatedInvestment);

        System.out.println(sb);
    }

}
