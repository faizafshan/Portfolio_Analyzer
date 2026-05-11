export interface Holding {
  symbol: string; name: string; sector: string; geography: string;
  weight: number; targetWeight: number; currentPrice: number; quantity: number;
  marketValue: number; dayChange: number; totalReturn: number; esgScore: number;
  beta: number; volatility: number;
}

export interface Portfolio {
  id: string; name: string; clientName: string; riskProfile: string;
  totalValue: number; benchmarkReturn: number; actualReturn: number;
  healthScore: number; complianceScore: number; esgScore: number; driftScore: number;
  currency: string; lastRebalanced: string; createdAt: string; holdings: Holding[];
}

export interface DriftItem {
  symbol: string; name: string; sector: string;
  currentWeight: number; targetWeight: number; driftPercent: number;
  driftDirection: string; severity: string;
}

export interface DriftAnalysis {
  portfolioId: string; overallDriftScore: number; severity: string;
  driftItems: DriftItem[]; alerts: string[];
}

export interface AgentStatus {
  agentId: string; agentName: string; description: string; status: string;
  lastOutput: string; confidenceScore: number; alertCount: number;
  lastRunTime: string; executionTimeMs: number;
}

export interface Recommendation {
  type: string; symbol: string; name: string; action: string;
  currentWeight: number; suggestedWeight: number; expectedImpact: number;
  priorityScore: number; reasoning: string; complianceStatus: string;
}

export interface OptimizationResult {
  portfolioId: string; currentReturn: number; projectedReturn: number;
  currentRisk: number; projectedRisk: number; currentSharpe: number; projectedSharpe: number;
  currentVolatility: number; projectedVolatility: number;
  riskReductionScore: number; returnImprovementScore: number; confidenceScore: number;
  recommendations: Recommendation[]; executiveSummary: string;
}

export interface ParsedConstraints {
  originalText: string; esgRequired: boolean; excludedSectors: string[];
  maxSingleStock: number; maxSectorExposure: number; maxDrawdown: number;
  minDiversificationRatio: number; riskProfile: string;
  additionalConstraints: Record<string, unknown>; confidenceScore: number; reasoning: string;
}

export interface ComplianceViolation {
  ruleId: string; ruleName: string; description: string; severity: string;
  currentValue: string; threshold: string; recommendation: string;
}

export interface ComplianceCheckResult {
  portfolioId: string; overallStatus: string; complianceScore: number;
  violations: ComplianceViolation[]; warnings: string[]; passedChecks: string[];
}

export interface ReasoningStep {
  step: number; agent: string; action: string; input: string; output: string; confidence: number;
}

export interface ExplainabilityReport {
  portfolioId: string; agentName: string; decision: string; reasoning: string;
  dataSources: string[]; reasoningChain: ReasoningStep[];
  featureImportance: Record<string, number>; confidenceScore: number;
}

export interface AuditEntry {
  id: string; portfolioId: string; agentName: string; action: string;
  description: string; reasoning: string; constraintMapping: string;
  complianceStatus: string; severity: string; confidenceScore: number; timestamp: string;
}

export interface ExecutiveSummary {
  portfolioId: string; portfolioName: string; healthScore: number; previousHealthScore: number;
  complianceScore: number; esgScore: number; driftSeverity: number; optimizationROI: number;
  summaryText: string; riskOutlook: string; urgentAlerts: number;
  pendingRecommendations: number; marketCondition: string;
}

export interface EsgHolding {
  symbol: string; name: string; esgScore: number; esgRating: string;
  controversyFlag: boolean; issues: string[];
}

export interface EsgAnalysis {
  portfolioId: string; overallEsgScore: number; environmentalScore: number;
  socialScore: number; governanceScore: number; complianceStatus: string;
  holdings: EsgHolding[]; controversies: string[]; excludedSectorViolations: string[];
}
