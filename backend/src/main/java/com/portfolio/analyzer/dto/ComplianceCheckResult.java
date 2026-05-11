package com.portfolio.analyzer.dto;

import java.util.List;
import java.util.Map;

public record ComplianceCheckResult(
        String portfolioId,
        String overallStatus,
        double complianceScore,
        List<ComplianceViolation> violations,
        List<String> warnings,
        List<String> passedChecks
) {
    public record ComplianceViolation(
            String ruleId,
            String ruleName,
            String description,
            String severity,
            String currentValue,
            String threshold,
            String recommendation
    ) {}
}
