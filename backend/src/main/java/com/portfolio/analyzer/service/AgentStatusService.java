package com.portfolio.analyzer.service;

import com.portfolio.analyzer.entity.AgentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgentStatusService {

    public List<AgentStatus> getAllAgentStatuses() {
        return List.of(
                new AgentStatus("agent-drift", "Portfolio Drift Detection Agent",
                        "Monitors allocation drift, sector overexposure, geography imbalance, concentration risk, beta mismatch, and volatility spikes",
                        "ACTIVE", "Technology allocation exceeds mandate by 12.4%. NVDA overweight by 3.5% — HIGH severity.",
                        0.95, 3, LocalDateTime.now().minusMinutes(2), 1240),
                new AgentStatus("agent-constraint", "Constraint Intelligence Agent",
                        "Converts investment policy documents into executable constraints using NLP extraction pipeline",
                        "ACTIVE", "Parsed 3 policy documents. Extracted 12 constraints. ESG mandate detected for 2 portfolios.",
                        0.89, 0, LocalDateTime.now().minusMinutes(5), 890),
                new AgentStatus("agent-optimizer", "Optimization Recommendation Agent",
                        "Generates rebalance suggestions, buy/sell recommendations, tax optimization, and diversification opportunities",
                        "ACTIVE", "Generated 8 recommendations across 3 portfolios. Projected ROI improvement: +2.4%.",
                        0.87, 2, LocalDateTime.now().minusMinutes(1), 2100),
                new AgentStatus("agent-esg", "ESG Scoring Agent",
                        "Analyzes ESG compliance, sustainability alignment, controversy detection, and excluded sector monitoring",
                        "ACTIVE", "Portfolio ESG score: 71.4. 2 holdings flagged for controversy. Energy sector violation detected.",
                        0.88, 1, LocalDateTime.now().minusMinutes(3), 760),
                new AgentStatus("agent-compliance", "Compliance Guardrail Agent",
                        "Validates SEBI/SEC compliance, internal risk rules, IPS validation, concentration caps, and mandate restrictions",
                        "ACTIVE", "2 violations detected: single stock concentration (AAPL), sector limit breach (Technology). 3 warnings issued.",
                        0.97, 2, LocalDateTime.now().minusMinutes(1), 540),
                new AgentStatus("agent-explain", "Explainability Agent",
                        "Generates traceable reasoning chains, decision source mapping, audit explanations, and feature importance analysis",
                        "IDLE", "Last report: 6-step reasoning chain generated. Decision confidence: 87.5%. All sources traceable.",
                        0.91, 0, LocalDateTime.now().minusMinutes(8), 1450),
                new AgentStatus("agent-summary", "Executive Summary Agent",
                        "Generates boardroom-style portfolio health summaries, KPI trends, and action item briefs",
                        "ACTIVE", "Portfolio health: 72 → 84 (projected). 5 priority actions. Market outlook: cautiously optimistic.",
                        0.93, 0, LocalDateTime.now().minusMinutes(4), 680)
        );
    }
}
