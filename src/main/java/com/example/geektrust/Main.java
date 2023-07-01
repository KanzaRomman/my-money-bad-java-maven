package com.example.geektrust;

import com.example.geektrust.dtos.Investment;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Month;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static double[] portfolioPercent = new double[3];
    int count = 0;

    enum Command  {
        ALLOCATE,
        SIP,
        CHANGE,
        BALANCE,
        REBALANCE
    }

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();

        List<Double> sip = new LinkedList<>();
        Investment updatedInvestment = new Investment();
        Investment investment = new Investment();

        int count = 0;

        try (Stream<String> fileLines = Files.lines(new File(args[0]).toPath())) {
            List<String> lines = fileLines.map(String::trim).filter(s -> !s.matches(" ")).collect(Collectors.toList());

            for (String line : lines) {
                String[] instructions = line.trim().split(" ");

                Command command = Command.valueOf(instructions[0]);

                switch (command) {
                    case ALLOCATE:
                        count = allocateMoney(portfolio, investment, count, instructions);
                        break;
                    case SIP:
                        for (int i = 1; i < instructions.length; i++) {
                            sip.add(Double.parseDouble(instructions[i]));
                        }
                        break;
                    case CHANGE:
                        count = changeGains(portfolio, sip, instructions, count);
                        break;
                    case BALANCE:
                        printBalance(portfolio, Month.valueOf(instructions[1]).getMonthNumber() - 1);
                        break;
                    case REBALANCE:
                        int size = portfolio.getPortfolioSize() - 1;
                        if (size % 6 == 0) {
                            printRebalance(portfolio, updatedInvestment, count);
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

    public static int changeGains(Portfolio portfolio, List<Double> sip, String[] instructions, int count) {
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
                    double s1 = temp + sip.get(i - 1);
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

        return count;
    }

    public static int allocateMoney(Portfolio portfolio, Investment investment, int count,
                                    String[] instructions) {
        double total;
        double temp;
        total = 0;

        for (int i = 1; i < instructions.length; i++) {
            temp = Double.parseDouble(instructions[i]);
            total += temp;
            investment.addToInvestment(temp);
        }
        investment.addToInvestment(total);

        portfolio.addToPortfolio(count, investment);

        calculatePercent(investment, total);

        count++;
        return count;
    }

    public static void calculatePercent(Investment investment, double total) {
        for (int i = 0; i < investment.getInvestmentCount()-1; i++) {
            portfolioPercent[i] = investment.getInvestment(i) / total;
        }
    }

    public static void printRebalance(Portfolio portfolio, Investment updatedInvestment, int count) {
        double total;
        Investment listValues;
        Double printValue;
        StringBuilder sb = new StringBuilder();

        listValues = portfolio.getInvestmentByMonth(count - 1);

        total = listValues.getInvestment(listValues.getInvestmentCount() - 1);

        for (double d : portfolioPercent) {
            updatedInvestment.addToInvestment(d * total);
            printValue = d * total;
            sb.append(printValue.shortValue());
            sb.append(" ");
        }

        updatedInvestment.addToInvestment(total);

        portfolio.addToPortfolio(count - 1, updatedInvestment);

        System.out.println(sb);
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
}
