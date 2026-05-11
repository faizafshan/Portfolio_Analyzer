package com.portfolio.analyzer.dto;

public record ExecutiveSummary(
        String portfolioId,
        String portfolioName,
        double healthScore,
        double previousHealthScore,
        double complianceScore,
        double esgScore,
        double driftSeverity,
        double optimizationROI,
        String summaryText,
        String riskOutlook,
        int urgentAlerts,
        int pendingRecommendations,
        String marketCondition
) {}
