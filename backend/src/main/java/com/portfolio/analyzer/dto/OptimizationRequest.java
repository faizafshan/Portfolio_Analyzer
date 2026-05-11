package com.portfolio.analyzer.dto;

import java.util.List;

public record OptimizationRequest(
        String portfolioId,
        String strategy,
        double riskTolerance,
        boolean esgConstraint,
        List<String> excludedSectors
) {}
