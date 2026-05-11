package com.portfolio.analyzer.dto;

import java.util.List;
import java.util.Map;

public record ExplainabilityReport(
        String portfolioId,
        String agentName,
        String decision,
        String reasoning,
        List<String> dataSources,
        List<ReasoningStep> reasoningChain,
        Map<String, Double> featureImportance,
        double confidenceScore
) {
    public record ReasoningStep(
            int step,
            String agent,
            String action,
            String input,
            String output,
            double confidence
    ) {}
}
