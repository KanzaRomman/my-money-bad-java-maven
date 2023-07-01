package com.example.geektrust.dtos;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Map<Integer, Investment> portfolios;

    public Portfolio() {
        this.portfolios = new HashMap<>();
    }

    public void addToPortfolio(int month, Investment investment) {
        this.portfolios.put(month, investment);
    }

    public Investment getInvestmentByMonth(int month) {
        return this.portfolios.get(month);
    }

    public int getPortfolioSize() {
        return portfolios.size();
    }
}
