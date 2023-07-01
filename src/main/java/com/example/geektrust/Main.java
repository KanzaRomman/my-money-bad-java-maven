package com.example.geektrust;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Month;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static int count = 0;

    enum Command  {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

    public static void main(String[] args) {

        try (Stream<String> fileLines = Files.lines(new File(args[0]).toPath())) {
            List<String> lines = fileLines.map(String::trim).filter(s -> !s.matches(" ")).collect(Collectors.toList());

            Portfolio portfolio = new Portfolio();
            Investment updatedInvestment = new Investment();
            Investment investment = new Investment();

            for (String line : lines) {
                String[] instructions = line.trim().split(" ");

                Command command = Command.valueOf(instructions[0]);

                switch (command) {
                    case ALLOCATE:
                        allocateFundsAndUpdatePortfolio(portfolio, investment, Arrays.copyOfRange(instructions, 1, instructions.length));
                        break;
                    case SIP:
                        addSipToPortfolio(portfolio, instruction);
                        break;
                    case CHANGE:
                        changeGains(portfolio, instructions);
                        break;
                    case BALANCE:
                        printBalance(portfolio, Month.valueOf(instructions[1]).getMonthNumber() - 1);
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

    private static void addSipToPortfolio(Portfolio portfolio, String[]instructions) {
        for (int i = 1; i < instructions.length; i++) {
            portfolio.addToSystematicInvestmentPlan(Double.parseDouble(instructions[i]));
        }
    }

    public static void allocateFundsAndUpdatePortfolio(
            Portfolio portfolio,
            Investment investment,
            String[] funds
    ) {

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
        Pattern p = Pattern.compile("^-?\\d+\\.?\\d+");
        Investment listValues = portfolio.getInvestmentByMonth(count - 1);

        Investment updatedInvestment = new Investment();

        double total = 0;

        for (int i = 1; i < instructions.length - 1; i++) {
            Matcher m = p.matcher(instructions[i]);
            if (m.find()) {
                double value = Double.parseDouble(m.group());

                double temp = listValues.getInvestment(i - 1);

                if (count - 1 > 0) {
                    double s1 = temp + portfolio.getSystematicInvestmentPlanAmount(i - 1);
                    double s2 = s1 * value;
                    double s3 = Math.floor(s2 / 100);
                    double s4 = s3 + s1;
                    updatedInvestment.addToInvestment(s4);
                    total += s4;
                } else {
                    double a1 = temp * value;
                    double a2 = Math.floor(a1 / 100);
                    double a3 = a2 + temp;
                    updatedInvestment.addToInvestment(a3);
                    total += a3;
                }
            }
        }
        updatedInvestment.addToInvestment(total);
        portfolio.addToPortfolio(count, updatedInvestment);

        count++;
    }

    public static String printBalance(Portfolio portfolio, int index) {
        Investment monthlyValues = portfolio.getInvestmentByMonth(index + 1);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < monthlyValues.getInvestmentCount() - 1; i++) {
            sb.append(monthlyValues.getInvestment(i).shortValue());
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

        total = listValues.getInvestment(listValues.getInvestmentCount() - 1);

        for (double d : portfolio.getAllocatedPercentage()) {
            updatedInvestment.addToInvestment(d * total);
            printValue = d * total;
            sb.append(printValue.shortValue());
            sb.append(" ");
        }

        updatedInvestment.addToInvestment(total);

        portfolio.addToPortfolio(count - 1, updatedInvestment);

        System.out.println(sb);
    }

}
