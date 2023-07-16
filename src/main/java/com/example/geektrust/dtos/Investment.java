package com.example.geektrust.dtos;

import java.util.ArrayList;
import java.util.List;

import static com.example.geektrust.helpers.MathHelper.computeFlooredPercentage;

public class Investment {
    private final List<Double> investments;
    private Double totalInvestment;

    public Investment() {
        investments = new ArrayList<>();
    }

    public void addToInvestment(Double fund) {
        investments.add(fund);
    }

    public void allocateFunds(
            String[] funds
    ) {
        for (String fund : funds) {
            addToInvestment(
                    Double.parseDouble(fund)
            );
        }
        setTotalInvestment();
    }

    public Double getInvestmentValue(int index) {
        return investments.get(index);
    }

    public int getInvestmentCount() {
        return investments.size();
    }

    public Double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment() {
        this.totalInvestment = investments.stream().mapToDouble(Double::doubleValue).sum();
    }

    public double getInvestmentPercentage(int index) {
        return this.getInvestmentValue(index) / this.getTotalInvestment();
    }

    public void updateInvestmentAfterMarketChange(
            Portfolio portfolio,
            List<Double> marketChangeValues
    ) {
        for (int i = 0; i < marketChangeValues.size(); i++) {
            double latestInvestmentValue = portfolio.getLatestInvestmentValue(i);
            double marketChangeValue = marketChangeValues.get(i);

            double investmentValueAfterMarketChange = computeInvestmentValueAfterMarketChange(
                    latestInvestmentValue,
                    marketChangeValue
            );
            addToInvestment(investmentValueAfterMarketChange);
        }
        setTotalInvestment();
    }

    private double computeInvestmentValueAfterMarketChange(
            double latestInvestmentValue,
            double marketChangeValue
    ) {
        double percentChangeInInvestment = computeFlooredPercentage(latestInvestmentValue, marketChangeValue);
        return percentChangeInInvestment + latestInvestmentValue;
    }

    public void displayBalance() {
        String balanceString = buildBalanceString();
        System.out.println(balanceString);
    }

    public String buildBalanceString() {
        StringBuilder stringBuilder = new StringBuilder();
        String separator = " ";
        for (int i = 0; i < getInvestmentCount(); i++) {
            stringBuilder.append(getInvestmentValue(i).shortValue());
            stringBuilder.append(separator);
        }
        return stringBuilder.toString();
    }

}
