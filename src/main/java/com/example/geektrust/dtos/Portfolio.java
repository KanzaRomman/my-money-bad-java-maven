package com.example.geektrust.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private final Map<Integer, Investment> portfolios;
    private final List<Double> systematicInvestmentPlan;

    public Portfolio() {
        portfolios = new HashMap<>();
        systematicInvestmentPlan = new ArrayList<>();
    }

    public void addToPortfolio(int month, Investment investment) {
        portfolios.put(month, investment);
    }

    public Investment getInvestmentByMonth(int month) {
        return portfolios.get(month);
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
}
