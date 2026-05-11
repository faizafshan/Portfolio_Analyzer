package com.portfolio.analyzer.entity;

import java.time.LocalDateTime;
import java.util.List;

public record Portfolio(
        String id,
        String name,
        String clientName,
        String riskProfile,
        double totalValue,
        double benchmarkReturn,
        double actualReturn,
        double healthScore,
        double complianceScore,
        double esgScore,
        double driftScore,
        String currency,
        LocalDateTime lastRebalanced,
        LocalDateTime createdAt,
        List<Holding> holdings
) {}
