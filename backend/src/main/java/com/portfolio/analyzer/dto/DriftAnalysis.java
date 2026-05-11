package com.portfolio.analyzer.dto;

import java.util.List;
import java.util.Map;

public record DriftAnalysis(
        String portfolioId,
        double overallDriftScore,
        String severity,
        List<DriftItem> driftItems,
        List<String> alerts
) {
    public record DriftItem(
            String symbol,
            String name,
            String sector,
            double currentWeight,
            double targetWeight,
            double driftPercent,
            String driftDirection,
            String severity
    ) {}
}
