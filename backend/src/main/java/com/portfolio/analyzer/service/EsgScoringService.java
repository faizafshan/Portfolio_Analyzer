package com.portfolio.analyzer.service;

import com.portfolio.analyzer.dto.EsgAnalysis;
import com.portfolio.analyzer.dto.EsgAnalysis.EsgHolding;
import com.portfolio.analyzer.entity.Holding;
import com.portfolio.analyzer.entity.Portfolio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsgScoringService {

    public EsgAnalysis analyze(Portfolio portfolio) {
        List<EsgHolding> esgHoldings = new ArrayList<>();
        List<String> controversies = new ArrayList<>();
        List<String> sectorViolations = new ArrayList<>();

        double totalEsg = 0;
        double totalEnv = 0, totalSoc = 0, totalGov = 0;
        int count = 0;

        for (Holding h : portfolio.holdings()) {
            if ("Cash".equals(h.sector()) || "Fixed Income".equals(h.sector())) continue;
            count++;

            String rating = h.esgScore() >= 80 ? "AAA" : h.esgScore() >= 70 ? "AA" :
                    h.esgScore() >= 60 ? "A" : h.esgScore() >= 50 ? "BBB" : "BB";

            boolean controversy = h.esgScore() < 50;
            List<String> issues = new ArrayList<>();
            if (h.esgScore() < 60) issues.add("Below ESG threshold");
            if ("Energy".equals(h.sector())) {
                issues.add("Carbon intensive sector");
                sectorViolations.add(h.name() + " — Energy sector may conflict with ESG mandates");
            }

            if (controversy) {
                controversies.add(h.name() + " — Flagged for ESG controversy (score: " + h.esgScore() + ")");
            }

            esgHoldings.add(new EsgHolding(h.symbol(), h.name(), h.esgScore(), rating, controversy, issues));

            totalEsg += h.esgScore();
            totalEnv += h.esgScore() * 0.95 + (Math.random() * 10 - 5);
            totalSoc += h.esgScore() * 1.02 + (Math.random() * 8 - 4);
            totalGov += h.esgScore() * 0.98 + (Math.random() * 6 - 3);
        }

        double avg = count > 0 ? totalEsg / count : 0;
        String status = avg >= 70 ? "COMPLIANT" : avg >= 55 ? "PARTIAL" : "NON_COMPLIANT";

        return new EsgAnalysis(
                portfolio.id(),
                Math.round(avg * 10.0) / 10.0,
                Math.round(totalEnv / count * 10.0) / 10.0,
                Math.round(totalSoc / count * 10.0) / 10.0,
                Math.round(totalGov / count * 10.0) / 10.0,
                status, esgHoldings, controversies, sectorViolations
        );
    }
}
