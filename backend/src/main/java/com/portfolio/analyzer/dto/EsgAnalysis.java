package com.portfolio.analyzer.dto;

import java.util.List;
import java.util.Map;

public record EsgAnalysis(
        String portfolioId,
        double overallEsgScore,
        double environmentalScore,
        double socialScore,
        double governanceScore,
        String complianceStatus,
        List<EsgHolding> holdings,
        List<String> controversies,
        List<String> excludedSectorViolations
) {
    public record EsgHolding(
            String symbol,
            String name,
            double esgScore,
            String esgRating,
            boolean controversyFlag,
            List<String> issues
    ) {}
}
