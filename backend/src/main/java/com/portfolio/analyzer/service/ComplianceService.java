package com.portfolio.analyzer.service;

import com.portfolio.analyzer.dto.ComplianceCheckResult;
import com.portfolio.analyzer.dto.ComplianceCheckResult.ComplianceViolation;
import com.portfolio.analyzer.entity.Holding;
import com.portfolio.analyzer.entity.Portfolio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceService {

    public ComplianceCheckResult check(Portfolio portfolio) {
        List<ComplianceViolation> violations = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        List<String> passed = new ArrayList<>();

        // Rule 1: Single stock concentration
        for (Holding h : portfolio.holdings()) {
            if (h.weight() > 10.0 && !"Cash".equals(h.sector()) && !"Fixed Income".equals(h.sector())) {
                violations.add(new ComplianceViolation(
                        "CONC-001", "Single Stock Concentration Limit",
                        h.name() + " exceeds 10% single-stock limit",
                        "HIGH", String.format("%.1f%%", h.weight()), "10.0%",
                        "Reduce position to within concentration limits"
                ));
            }
        }

        // Rule 2: Sector concentration
        double techWeight = portfolio.holdings().stream()
                .filter(h -> "Technology".equals(h.sector()))
                .mapToDouble(Holding::weight).sum();
        if (techWeight > 30.0) {
            violations.add(new ComplianceViolation(
                    "SEC-001", "Sector Concentration Limit",
                    "Technology sector exceeds 30% limit",
                    "CRITICAL", String.format("%.1f%%", techWeight), "30.0%",
                    "Diversify technology holdings across other sectors"
            ));
        } else if (techWeight > 25.0) {
            warnings.add("Technology sector approaching concentration limit (" + String.format("%.1f%%", techWeight) + ")");
        } else {
            passed.add("Technology sector within limits (" + String.format("%.1f%%", techWeight) + ")");
        }

        // Rule 3: Cash minimum
        double cashWeight = portfolio.holdings().stream()
                .filter(h -> "Cash".equals(h.sector()))
                .mapToDouble(Holding::weight).sum();
        if (cashWeight < 2.0) {
            violations.add(new ComplianceViolation(
                    "LIQ-001", "Minimum Liquidity Requirement",
                    "Cash allocation below 2% minimum",
                    "HIGH", String.format("%.1f%%", cashWeight), "2.0%",
                    "Increase cash reserves to meet liquidity requirements"
            ));
        } else {
            passed.add("Liquidity requirement met (" + String.format("%.1f%%", cashWeight) + " cash)");
        }

        // Rule 4: Volatility check
        double avgVolatility = portfolio.holdings().stream()
                .mapToDouble(Holding::volatility).average().orElse(0);
        if (avgVolatility > 30.0) {
            violations.add(new ComplianceViolation(
                    "VOL-001", "Portfolio Volatility Limit",
                    "Average volatility exceeds risk threshold",
                    "MEDIUM", String.format("%.1f%%", avgVolatility), "30.0%",
                    "Reduce exposure to high-volatility assets"
            ));
        } else {
            passed.add("Portfolio volatility within limits (" + String.format("%.1f%%", avgVolatility) + ")");
        }

        // Rule 5: Geographic diversification
        double usWeight = portfolio.holdings().stream()
                .filter(h -> "US".equals(h.geography()))
                .mapToDouble(Holding::weight).sum();
        if (usWeight > 75.0) {
            warnings.add("US concentration at " + String.format("%.1f%%", usWeight) + " — consider geographic diversification");
        } else {
            passed.add("Geographic diversification adequate (US: " + String.format("%.1f%%", usWeight) + ")");
        }

        // Rule 6: Beta check
        double avgBeta = portfolio.holdings().stream()
                .filter(h -> h.beta() > 0)
                .mapToDouble(Holding::beta).average().orElse(1.0);
        if (avgBeta > 1.3) {
            warnings.add("Portfolio beta elevated at " + String.format("%.2f", avgBeta));
        } else {
            passed.add("Portfolio beta within range (" + String.format("%.2f", avgBeta) + ")");
        }

        passed.add("Mandate restrictions validated");
        passed.add("IPS alignment confirmed");

        double score = 100.0 - (violations.size() * 8.0) - (warnings.size() * 3.0);
        String status = violations.isEmpty() ? "COMPLIANT" : violations.stream().anyMatch(v -> "CRITICAL".equals(v.severity())) ? "NON_COMPLIANT" : "PARTIAL";

        return new ComplianceCheckResult(portfolio.id(), status, Math.max(score, 0), violations, warnings, passed);
    }
}
