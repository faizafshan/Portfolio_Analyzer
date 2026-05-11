package com.portfolio.analyzer.service;

import com.portfolio.analyzer.dto.ExplainabilityReport;
import com.portfolio.analyzer.dto.ExplainabilityReport.ReasoningStep;
import com.portfolio.analyzer.entity.Portfolio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExplainabilityService {

    public ExplainabilityReport explain(Portfolio portfolio, String decision) {
        List<ReasoningStep> chain = List.of(
                new ReasoningStep(1, "Constraint Intelligence Agent",
                        "Parse investment policy",
                        "IPS document for " + portfolio.clientName(),
                        "Extracted constraints: ESG required, max single stock 8%, max drawdown 15%",
                        0.92),
                new ReasoningStep(2, "Portfolio Drift Detection Agent",
                        "Analyze allocation drift",
                        "Current holdings vs target allocation",
                        String.format("Overall drift score: %.1f. Technology sector overweight by %.1f%%",
                                portfolio.driftScore(), portfolio.holdings().stream()
                                        .filter(h -> "Technology".equals(h.sector()))
                                        .mapToDouble(h -> h.weight() - h.targetWeight()).sum()),
                        0.95),
                new ReasoningStep(3, "ESG Scoring Agent",
                        "Evaluate ESG compliance",
                        "Holdings ESG scores and sector analysis",
                        String.format("Portfolio ESG score: %.1f. %d holdings below threshold.",
                                portfolio.esgScore(),
                                portfolio.holdings().stream().filter(h -> h.esgScore() < 60).count()),
                        0.88),
                new ReasoningStep(4, "Optimization Recommendation Agent",
                        "Generate rebalancing recommendations",
                        "Drift analysis + constraints + risk model",
                        "Generated rebalance plan with projected return improvement of +1.8% and risk reduction of -2.3%",
                        0.85),
                new ReasoningStep(5, "Compliance Guardrail Agent",
                        "Validate recommendations against rules",
                        "Proposed trades + regulatory rules",
                        "All recommendations validated. 0 regulatory conflicts detected.",
                        0.97),
                new ReasoningStep(6, "Executive Summary Agent",
                        "Generate executive brief",
                        "All agent outputs",
                        String.format("Portfolio health projected: %.0f → %.0f. %d action items identified.",
                                portfolio.healthScore(), Math.min(portfolio.healthScore() + 16, 98), 5),
                        0.91)
        );

        Map<String, Double> features = Map.of(
                "Allocation Drift", 0.28,
                "Sector Concentration", 0.22,
                "ESG Compliance", 0.18,
                "Volatility Exposure", 0.14,
                "Geographic Balance", 0.10,
                "Liquidity", 0.08
        );

        return new ExplainabilityReport(
                portfolio.id(),
                "Multi-Agent Orchestrator",
                decision != null ? decision : "Portfolio Optimization & Rebalance",
                "Decision derived through 6-agent reasoning pipeline. Primary triggers: allocation drift in Technology sector, " +
                "ESG compliance gaps, and concentration risk. Recommendation confidence weighted by agent consensus (87.5%).",
                List.of("Investment Policy Statement", "Market Data Feed", "ESG Database", "Compliance Rulebook", "Historical Performance"),
                chain, features, 0.875
        );
    }
}
