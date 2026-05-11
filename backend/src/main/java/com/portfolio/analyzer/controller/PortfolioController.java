package com.portfolio.analyzer.controller;

import com.portfolio.analyzer.dto.*;
import com.portfolio.analyzer.entity.Portfolio;
import com.portfolio.analyzer.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@Tag(name = "Portfolio", description = "Portfolio management and analysis APIs")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final DriftDetectionService driftService;

    public PortfolioController(PortfolioService portfolioService, DriftDetectionService driftService) {
        this.portfolioService = portfolioService;
        this.driftService = driftService;
    }

    @GetMapping
    @Operation(summary = "Get all portfolios")
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String id) {
        return portfolioService.getPortfolio(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/drift/{id}")
    @Operation(summary = "Get drift analysis for a portfolio")
    public ResponseEntity<DriftAnalysis> getDriftAnalysis(@PathVariable String id) {
        return portfolioService.getPortfolio(id)
                .map(p -> ResponseEntity.ok(driftService.analyzeDrift(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/analysis/{id}")
    @Operation(summary = "Get comprehensive analysis for a portfolio")
    public ResponseEntity<PortfolioAnalysis> getAnalysis(@PathVariable String id) {
        return portfolioService.getPortfolio(id)
                .map(p -> ResponseEntity.ok(new PortfolioAnalysis(p, driftService.analyzeDrift(p))))
                .orElse(ResponseEntity.notFound().build());
    }

    public record PortfolioAnalysis(Portfolio portfolio, DriftAnalysis driftAnalysis) {}
}
