package com.portfolio.analyzer.entity;

import java.time.LocalDateTime;

public record AgentStatus(
        String agentId,
        String agentName,
        String description,
        String status,
        String lastOutput,
        double confidenceScore,
        int alertCount,
        LocalDateTime lastRunTime,
        long executionTimeMs
) {}
