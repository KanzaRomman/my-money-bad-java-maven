package com.example.geektrust;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Month;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.example.geektrust.helpers.FileInstructionHelper.extractCommandFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.extractNumericValuesFromInstructions;
import static com.example.geektrust.helpers.FileInstructionHelper.getInstructionFromFileLine;
import static com.example.geektrust.helpers.FileInstructionHelper.getTrimmedLinesAsList;
import static com.example.geektrust.helpers.FileInstructionHelper.getValuesFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.readLinesFromFile;

public class Main {
    static int count = 0;

    public enum Command  {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

    public static void main(String[] args) {

//        Path filePath = new File(args[0]).toPath();
        Path filePath = new File("sample_input/input1.txt").toPath();
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
                        changeGains(portfolio, instruction);
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
        updatePortfolio(portfolio, investment, count);

        count++;
    }

    private static void allocateFunds(Investment investment, String[] funds) {

        for (String fund : funds) {
            investment.addToInvestment(
                    Double.parseDouble(fund)
            );
        }

        investment.setTotalInvestment();
    }

    private static void updatePortfolio(Portfolio portfolio, Investment investment, int count) {
        portfolio.addToPortfolio(count, investment);
        portfolio.setAllocatedPercentage();
    }

    public static void changeGains(Portfolio portfolio, String[] instructions) {
        Investment latestInvestment = portfolio.getInvestmentByMonth(count - 1);

        Investment investmentAfterMarketChange = new Investment();

        List<Double> marketChangeValues = extractNumericValuesFromInstructions(instructions);

        for (int i = 0; i < marketChangeValues.size(); i++) {

                double temp = latestInvestment.getInvestmentValue(i);

                if (count - 1 > 0) {
                    double s1 = temp + portfolio.getSystematicInvestmentPlanAmount(i);
                    double s2 = s1 * marketChangeValues.get(i);
                    double s3 = Math.floor(s2 / 100);
                    double s4 = s3 + s1;
                    investmentAfterMarketChange.addToInvestment(s4);
                } else {
                    double a1 = temp * marketChangeValues.get(i);
                    double a2 = Math.floor(a1 / 100);
                    double a3 = a2 + temp;
                    investmentAfterMarketChange.addToInvestment(a3);
                }
        }
        investmentAfterMarketChange.setTotalInvestment();
        portfolio.addToPortfolio(count, investmentAfterMarketChange);

        count++;
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

        listValues = portfolio.getInvestmentByMonth(count - 1);

        for (double d : portfolio.getAllocatedPercentage()) {
            updatedInvestment.addToInvestment(d * listValues.getTotalInvestment());
            printValue = d * listValues.getTotalInvestment();
            sb.append(printValue.shortValue());
            sb.append(" ");
        }

        updatedInvestment.setTotalInvestment();

        portfolio.addToPortfolio(count - 1, updatedInvestment);

        System.out.println(sb);
    }

}
