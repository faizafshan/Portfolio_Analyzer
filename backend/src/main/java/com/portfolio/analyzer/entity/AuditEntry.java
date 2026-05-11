package com.portfolio.analyzer.entity;

import java.time.LocalDateTime;

public record AuditEntry(
        String id,
        String portfolioId,
        String agentName,
        String action,
        String description,
        String reasoning,
        String constraintMapping,
        String complianceStatus,
        String severity,
        double confidenceScore,
        LocalDateTime timestamp
) {}
