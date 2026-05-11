package com.portfolio.analyzer.controller;

import com.portfolio.analyzer.entity.AuditEntry;
import com.portfolio.analyzer.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@Tag(name = "Audit", description = "Audit trail and recommendation tracking")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "Get all audit entries")
    public List<AuditEntry> getAllAuditEntries() {
        return auditService.getAuditTrail(null);
    }

    @GetMapping("/{portfolioId}")
    @Operation(summary = "Get audit trail for a portfolio")
    public List<AuditEntry> getAuditTrail(@PathVariable String portfolioId) {
        return auditService.getAuditTrail(portfolioId);
    }
}
