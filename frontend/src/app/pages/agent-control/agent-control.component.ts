import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { AppStateService } from '../../services/app-state.service';
import { AgentStatus, ExplainabilityReport } from '../../models/portfolio.model';

@Component({
  selector: 'app-agent-control',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agent-control.component.html',
  styleUrl: './agent-control.component.scss'
})
export class AgentControlComponent implements OnInit {
  agents: AgentStatus[] = [];
  explainability: ExplainabilityReport | null = null;
  loading = true;

  constructor(private api: ApiService, private state: AppStateService) {}

  ngOnInit(): void {
    this.api.agentStatus().subscribe(a => {
      this.agents = a;
      this.loading = false;
    });
    this.api.explain(this.state.selectedPortfolioId()).subscribe(e => this.explainability = e);
  }

  get activeCount(): number { return this.agents.filter(a => a.status === 'ACTIVE').length; }
  get avgConfidence(): number {
    if (!this.agents.length) return 0;
    return this.agents.reduce((a, b) => a + b.confidenceScore, 0) / this.agents.length;
  }
  get totalAlerts(): number { return this.agents.reduce((a, b) => a + b.alertCount, 0); }

  getStatusClass(status: string): string {
    switch (status?.toUpperCase()) {
      case 'ACTIVE': return 'badge-success';
      case 'IDLE': return 'badge-info';
      case 'WARNING': return 'badge-warning';
      default: return 'badge-info';
    }
  }
}
