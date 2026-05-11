package com.portfolio.analyzer.service;

import com.portfolio.analyzer.entity.AuditEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditService {

    private final List<AuditEntry> entries = List.of(
            new AuditEntry("AUD-001", "PF-001", "Drift Detection Agent", "DRIFT_ANALYSIS",
                    "Technology sector allocation exceeds mandate by 12.4%",
                    "Scanned 15 holdings against target allocation. Technology sector at 54.9% vs 45% target. Triggered by NVDA weight increase from market movement.",
                    "IPS Section 4.2 — Sector Limits", "VIOLATION", "HIGH", 0.95,
                    LocalDateTime.now().minusHours(2)),
            new AuditEntry("AUD-002", "PF-001", "Compliance Guardrail Agent", "COMPLIANCE_CHECK",
                    "Single stock concentration violation: AAPL at 15.2%",
                    "AAPL position exceeds 10% single-stock concentration limit defined in IPS. Current: 15.2%, Limit: 10.0%. Requires immediate rebalancing action.",
                    "SEBI Reg. 44(1) — Concentration Limits", "VIOLATION", "HIGH", 0.97,
                    LocalDateTime.now().minusHours(2)),
            new AuditEntry("AUD-003", "PF-001", "ESG Scoring Agent", "ESG_ANALYSIS",
                    "Portfolio ESG score below target: 71.4 vs 75.0 required",
                    "ESG analysis flagged 3 holdings with scores below threshold. XOM (32) identified as primary drag due to carbon-intensive operations.",
                    "ESG Policy — Minimum Score Requirement", "WARNING", "MEDIUM", 0.88,
                    LocalDateTime.now().minusHours(1)),
            new AuditEntry("AUD-004", "PF-001", "Optimization Agent", "RECOMMENDATION",
                    "Rebalance plan generated: 8 trade recommendations",
                    "Multi-objective optimization considering drift correction, ESG improvement, and risk reduction. Priority Score = Risk Impact + Return Improvement + Compliance Urgency.",
                    "Optimization Policy — Multi-Factor Model", "APPROVED", "INFO", 0.87,
                    LocalDateTime.now().minusMinutes(45)),
            new AuditEntry("AUD-005", "PF-001", "Explainability Agent", "EXPLANATION",
                    "6-step reasoning chain generated for portfolio rebalance decision",
                    "Full audit trail: Constraint parsing → Drift detection → ESG evaluation → Optimization → Compliance validation → Executive summary. All 6 agents contributed to final recommendation.",
                    "Explainability Framework — Full Chain", "COMPLETED", "INFO", 0.91,
                    LocalDateTime.now().minusMinutes(30)),
            new AuditEntry("AUD-006", "PF-001", "Executive Summary Agent", "SUMMARY",
                    "Portfolio health projected improvement: 72 → 84",
                    "Consolidated outputs from all agents. Health score improvement driven by: drift correction (+6), ESG compliance (+3), risk optimization (+3). 5 priority action items generated.",
                    "Executive Reporting — KPI Framework", "COMPLETED", "INFO", 0.93,
                    LocalDateTime.now().minusMinutes(15)),
            new AuditEntry("AUD-007", "PF-002", "Drift Detection Agent", "DRIFT_ANALYSIS",
                    "Portfolio within acceptable drift range (8.4%)",
                    "Conservative portfolio showing minimal drift. All positions within 2% of target. No immediate rebalancing required.",
                    "IPS Section 4.2 — Sector Limits", "COMPLIANT", "LOW", 0.96,
                    LocalDateTime.now().minusHours(3)),
            new AuditEntry("AUD-008", "PF-003", "ESG Scoring Agent", "ESG_ANALYSIS",
                    "ESG-focused portfolio scoring well: 83.6 overall",
                    "Sustainable Future ESG Fund meets all ESG mandates. 10 of 12 holdings rated AA or above. Clean Energy ETF contributing positively to overall score.",
                    "ESG Policy — Fund Mandate Compliance", "COMPLIANT", "LOW", 0.92,
                    LocalDateTime.now().minusHours(1)),
            new AuditEntry("AUD-009", "PF-001", "Constraint Intelligence Agent", "CONSTRAINT_PARSE",
                    "Investment policy updated: new ESG exclusion added",
                    "Client mandate update processed. New exclusion: fossil fuel companies with >30% revenue from coal. 2 holdings flagged for review. Constraint confidence: 89%.",
                    "IPS Amendment — ESG Exclusions", "PROCESSED", "MEDIUM", 0.89,
                    LocalDateTime.now().minusMinutes(50)),
            new AuditEntry("AUD-010", "PF-001", "Compliance Guardrail Agent", "FINAL_APPROVAL",
                    "Rebalance plan approved with 2 conditions",
                    "Final compliance review complete. Plan approved subject to: 1) AAPL reduction executed within 5 trading days, 2) ESG score re-evaluation post-rebalance. No regulatory blockers.",
                    "Compliance Framework — Approval Protocol", "APPROVED", "INFO", 0.97,
                    LocalDateTime.now().minusMinutes(10))
    );

    public List<AuditEntry> getAuditTrail(String portfolioId) {
        if (portfolioId == null || portfolioId.isBlank()) return entries;
        return entries.stream()
                .filter(e -> e.portfolioId().equals(portfolioId))
                .collect(Collectors.toList());
    }
}
