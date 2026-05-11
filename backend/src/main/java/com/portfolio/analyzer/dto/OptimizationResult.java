package com.portfolio.analyzer.dto;

import java.util.List;

public record OptimizationResult(
        String portfolioId,
        double currentReturn,
        double projectedReturn,
        double currentRisk,
        double projectedRisk,
        double currentSharpe,
        double projectedSharpe,
        double currentVolatility,
        double projectedVolatility,
        double riskReductionScore,
        double returnImprovementScore,
        double confidenceScore,
        List<Recommendation> recommendations,
        String executiveSummary
) {
    public record Recommendation(
            String type,
            String symbol,
            String name,
            String action,
            double currentWeight,
            double suggestedWeight,
            double expectedImpact,
            double priorityScore,
            String reasoning,
            String complianceStatus
    ) {}
}
