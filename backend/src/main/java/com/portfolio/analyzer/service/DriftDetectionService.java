package com.portfolio.analyzer.service;

import com.portfolio.analyzer.dto.DriftAnalysis;
import com.portfolio.analyzer.dto.DriftAnalysis.DriftItem;
import com.portfolio.analyzer.entity.Holding;
import com.portfolio.analyzer.entity.Portfolio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriftDetectionService {

    public DriftAnalysis analyzeDrift(Portfolio portfolio) {
        List<DriftItem> items = new ArrayList<>();
        List<String> alerts = new ArrayList<>();
        double totalDrift = 0;

        for (Holding h : portfolio.holdings()) {
            double drift = h.weight() - h.targetWeight();
            double absDrift = Math.abs(drift);
            totalDrift += absDrift;

            String severity = absDrift > 3.0 ? "HIGH" : absDrift > 1.5 ? "MEDIUM" : "LOW";
            String direction = drift > 0 ? "OVERWEIGHT" : drift < 0 ? "UNDERWEIGHT" : "ON_TARGET";

            items.add(new DriftItem(
                    h.symbol(), h.name(), h.sector(),
                    h.weight(), h.targetWeight(),
                    Math.round(drift * 100.0) / 100.0,
                    direction, severity
            ));

            if (absDrift > 3.0) {
                alerts.add(String.format("%s allocation drifted %.1f%% from target — %s at %.1f%% vs target %.1f%%",
                        h.name(), absDrift, direction.toLowerCase(), h.weight(), h.targetWeight()));
            }
        }

        // Sector concentration check
        double techWeight = portfolio.holdings().stream()
                .filter(h -> "Technology".equals(h.sector()))
                .mapToDouble(Holding::weight).sum();
        if (techWeight > 30) {
            alerts.add(String.format("Technology sector concentration at %.1f%% exceeds 30%% mandate limit", techWeight));
        }

        double overallDrift = Math.round((totalDrift / portfolio.holdings().size()) * 100.0) / 100.0;
        String overallSeverity = overallDrift > 3.0 ? "CRITICAL" : overallDrift > 2.0 ? "HIGH" : overallDrift > 1.0 ? "MEDIUM" : "LOW";

        return new DriftAnalysis(portfolio.id(), overallDrift, overallSeverity, items, alerts);
    }
}
