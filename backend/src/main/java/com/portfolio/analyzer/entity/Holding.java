package com.portfolio.analyzer.entity;

public record Holding(
        String symbol,
        String name,
        String sector,
        String geography,
        double weight,
        double targetWeight,
        double currentPrice,
        double quantity,
        double marketValue,
        double dayChange,
        double totalReturn,
        double esgScore,
        double beta,
        double volatility
) {}
