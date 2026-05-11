package com.portfolio.analyzer.controller;

import com.portfolio.analyzer.dto.*;
import com.portfolio.analyzer.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Agents", description = "Multi-agent AI analysis and optimization APIs")
public class AiController {

    private final PortfolioService portfolioService;
    private final OptimizationService optimizationService;
    private final ConstraintIntelligenceService constraintService;
    private final ComplianceService complianceService;
    private final ExplainabilityService explainabilityService;
    private final EsgScoringService esgService;
    private final AgentStatusService agentStatusService;

    public AiController(PortfolioService portfolioService,
                        OptimizationService optimizationService,
                        ConstraintIntelligenceService constraintService,
                        ComplianceService complianceService,
                        ExplainabilityService explainabilityService,
                        EsgScoringService esgService,
                        AgentStatusService agentStatusService) {
        this.portfolioService = portfolioService;
        this.optimizationService = optimizationService;
        this.constraintService = constraintService;
        this.complianceService = complianceService;
        this.explainabilityService = explainabilityService;
        this.esgService = esgService;
        this.agentStatusService = agentStatusService;
    }

    @PostMapping("/optimize")
    @Operation(summary = "Run optimization on a portfolio")
    public ResponseEntity<OptimizationResult> optimize(@RequestBody OptimizationRequest request) {
        return portfolioService.getPortfolio(request.portfolioId())
                .map(p -> ResponseEntity.ok(optimizationService.optimize(p, request)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/constraints")
    @Operation(summary = "Parse investment policy into constraints")
    public ParsedConstraints parseConstraints(@RequestBody ConstraintParseRequest request) {
        return constraintService.parseConstraints(request);
    }

    @GetMapping("/compliance-check/{portfolioId}")
    @Operation(summary = "Run compliance check on a portfolio")
    public ResponseEntity<ComplianceCheckResult> complianceCheck(@PathVariable String portfolioId) {
        return portfolioService.getPortfolio(portfolioId)
                .map(p -> ResponseEntity.ok(complianceService.check(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/explain/{portfolioId}")
    @Operation(summary = "Get explainability report for a portfolio")
    public ResponseEntity<ExplainabilityReport> explain(
            @PathVariable String portfolioId,
            @RequestParam(required = false) String decision) {
        return portfolioService.getPortfolio(portfolioId)
                .map(p -> ResponseEntity.ok(explainabilityService.explain(p, decision)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/esg/{portfolioId}")
    @Operation(summary = "Get ESG analysis for a portfolio")
    public ResponseEntity<EsgAnalysis> esgAnalysis(@PathVariable String portfolioId) {
        return portfolioService.getPortfolio(portfolioId)
                .map(p -> ResponseEntity.ok(esgService.analyze(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/agent-status")
    @Operation(summary = "Get status of all AI agents")
    public java.util.List<com.portfolio.analyzer.entity.AgentStatus> getAgentStatuses() {
        return agentStatusService.getAllAgentStatuses();
    }

    @GetMapping("/executive-summary/{portfolioId}")
    @Operation(summary = "Get executive summary for a portfolio")
    public ResponseEntity<ExecutiveSummary> executiveSummary(@PathVariable String portfolioId) {
        return portfolioService.getPortfolio(portfolioId)
                .map(p -> ResponseEntity.ok(new ExecutiveSummary(
                        p.id(), p.name(), p.healthScore(), p.healthScore() - 12,
                        p.complianceScore(), p.esgScore(), p.driftScore(),
                        2.4,
                        String.format("Portfolio health score improved from %.0f → %.0f after optimization. " +
                                "Compliance maintained at %.0f%%. ESG score: %.1f. " +
                                "%d urgent actions required. Market outlook: cautiously optimistic.",
                                p.healthScore() - 12, p.healthScore(), p.complianceScore(),
                                p.esgScore(), p.driftScore() > 20 ? 3 : 1),
                        p.driftScore() > 20 ? "Elevated risk — rebalancing recommended" : "Stable — monitoring",
                        p.driftScore() > 20 ? 3 : 1,
                        p.driftScore() > 15 ? 5 : 2,
                        "Cautiously Optimistic"
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
