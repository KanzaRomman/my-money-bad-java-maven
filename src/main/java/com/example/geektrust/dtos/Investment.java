package com.example.geektrust.dtos;

import java.util.ArrayList;
import java.util.List;

public class Investment {
    private final List<Double> investments;
    private Double totalInvestment;

    public Investment() {
        investments = new ArrayList<>();
    }

    public void addToInvestment(Double fund) {
        investments.add(fund);
    }

    public Double getInvestment(int index) {
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
        return this.getInvestment(index) / this.getTotalInvestment();
    }
}
