import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent) },
  { path: 'portfolio/:id', loadComponent: () => import('./pages/portfolio-analysis/portfolio-analysis.component').then(m => m.PortfolioAnalysisComponent) },
  { path: 'agents', loadComponent: () => import('./pages/agent-control/agent-control.component').then(m => m.AgentControlComponent) },
  { path: 'constraints', loadComponent: () => import('./pages/constraint-intelligence/constraint-intelligence.component').then(m => m.ConstraintIntelligenceComponent) },
  { path: 'simulator', loadComponent: () => import('./pages/simulator/simulator.component').then(m => m.SimulatorComponent) },
  { path: 'audit', loadComponent: () => import('./pages/audit-trail/audit-trail.component').then(m => m.AuditTrailComponent) },
  { path: '**', redirectTo: '' }
];
