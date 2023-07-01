package com.example.geektrust.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private final Map<Integer, List<Double>> portfolios;

    public Portfolio() {
        this.portfolios = new HashMap<>();
    }

    public void addToPortfolio(int month, List<Double> investment) {
        this.portfolios.put(month, investment);
    }

    public List<Double> getInvestmentByMonth(int month) {
        return this.portfolios.get(month);
    }

    public int getPortfolioSize() {
        return portfolios.size();
    }
}
