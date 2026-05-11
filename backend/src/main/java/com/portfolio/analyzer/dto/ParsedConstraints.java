package com.portfolio.analyzer.dto;

import java.util.List;
import java.util.Map;

public record ParsedConstraints(
        String originalText,
        boolean esgRequired,
        List<String> excludedSectors,
        double maxSingleStock,
        double maxSectorExposure,
        double maxDrawdown,
        double minDiversificationRatio,
        String riskProfile,
        Map<String, Object> additionalConstraints,
        double confidenceScore,
        String reasoning
) {}
