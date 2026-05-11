import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Portfolio, DriftAnalysis, AgentStatus, OptimizationResult,
  ParsedConstraints, ComplianceCheckResult, ExplainabilityReport,
  AuditEntry, ExecutiveSummary, EsgAnalysis
} from '../models/portfolio.model';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly base = '/api';

  constructor(private http: HttpClient) {}

  getPortfolios(): Observable<Portfolio[]> {
    return this.http.get<Portfolio[]>(`${this.base}/portfolio`);
  }

  getPortfolio(id: string): Observable<Portfolio> {
    return this.http.get<Portfolio>(`${this.base}/portfolio/${id}`);
  }

  getDrift(id: string): Observable<DriftAnalysis> {
    return this.http.get<DriftAnalysis>(`${this.base}/portfolio/drift/${id}`);
  }

  optimize(portfolioId: string, strategy = 'balanced', riskTolerance = 0.5): Observable<OptimizationResult> {
    return this.http.post<OptimizationResult>(`${this.base}/ai/optimize`, {
      portfolioId, strategy, riskTolerance, esgConstraint: true, excludedSectors: []
    });
  }

  parseConstraints(policyText: string): Observable<ParsedConstraints> {
    return this.http.post<ParsedConstraints>(`${this.base}/ai/constraints`, { policyText });
  }

  complianceCheck(id: string): Observable<ComplianceCheckResult> {
    return this.http.get<ComplianceCheckResult>(`${this.base}/ai/compliance-check/${id}`);
  }

  explain(id: string): Observable<ExplainabilityReport> {
    return this.http.get<ExplainabilityReport>(`${this.base}/ai/explain/${id}`);
  }

  esgAnalysis(id: string): Observable<EsgAnalysis> {
    return this.http.get<EsgAnalysis>(`${this.base}/ai/esg/${id}`);
  }

  agentStatus(): Observable<AgentStatus[]> {
    return this.http.get<AgentStatus[]>(`${this.base}/ai/agent-status`);
  }

  executiveSummary(id: string): Observable<ExecutiveSummary> {
    return this.http.get<ExecutiveSummary>(`${this.base}/ai/executive-summary/${id}`);
  }

  getAuditTrail(portfolioId?: string): Observable<AuditEntry[]> {
    const url = portfolioId ? `${this.base}/audit/${portfolioId}` : `${this.base}/audit`;
    return this.http.get<AuditEntry[]>(url);
  }
}
