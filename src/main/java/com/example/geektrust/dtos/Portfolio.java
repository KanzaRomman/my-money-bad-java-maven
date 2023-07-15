package com.example.geektrust.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private final Map<Integer, Investment> portfolios;
    private final List<Double> systematicInvestmentPlan;
    private final List<Double> allocatedPercentage;
    private Integer operationCount = 0;
    private static final int INITIAL_MONTH = 0;

    public Portfolio() {
        portfolios = new HashMap<>();
        systematicInvestmentPlan = new ArrayList<>();
        allocatedPercentage = new ArrayList<>();
    }

    public void addToPortfolio(Investment investment) {
        portfolios.put(operationCount, investment);
    }

    public Investment getInvestmentByMonth(int month) {
        return portfolios.get(month);
    }

    public Investment getLatestInvestment() {
        int latestInvestmentOffset = operationCount - 1;
        return portfolios.get(latestInvestmentOffset);
    }

    public int getPortfolioSize() {
        return portfolios.size();
    }

    public void addToSystematicInvestmentPlan(Double amount) {
        systematicInvestmentPlan.add(amount);
    }

    public Double getSystematicInvestmentPlanAmount(int index) {
        return systematicInvestmentPlan.get(index);
    }

    public void setAllocatedPercentage() {
        Investment initialInvestment = getInvestmentByMonth(INITIAL_MONTH);
        for (int investmentIndex = 0; investmentIndex < initialInvestment.getInvestmentCount(); investmentIndex++) {
            double investmentPercentage = initialInvestment.getInvestmentPercentage(investmentIndex);
            allocatedPercentage.add(investmentIndex, investmentPercentage);
        }
    }

    public List<Double> getAllocatedPercentage() {
        return this.allocatedPercentage;
    }

    public void recordOperation() {
        operationCount++;
    }

    public Integer getOperationCount() {
        return operationCount;
    }
}
