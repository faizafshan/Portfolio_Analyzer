import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { AppStateService } from '../../services/app-state.service';
import { AuditEntry, Portfolio } from '../../models/portfolio.model';

@Component({
  selector: 'app-audit-trail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './audit-trail.component.html',
  styleUrl: './audit-trail.component.scss'
})
export class AuditTrailComponent implements OnInit {
  entries: AuditEntry[] = [];
  portfolios: Portfolio[] = [];
  filterAgent = '';
  filterSeverity = '';
  loading = true;

  constructor(public state: AppStateService, private api: ApiService) {}

  ngOnInit(): void {
    this.api.getPortfolios().subscribe(p => this.portfolios = p);
    this.loadEntries();
  }

  loadEntries(): void {
    this.loading = true;
    this.api.getAuditTrail(this.state.selectedPortfolioId()).subscribe(e => {
      this.entries = e;
      this.loading = false;
    });
  }

  get agents(): string[] {
    return [...new Set(this.entries.map(e => e.agentName))];
  }

  get filtered(): AuditEntry[] {
    return this.entries.filter(e =>
      (!this.filterAgent || e.agentName === this.filterAgent) &&
      (!this.filterSeverity || e.severity === this.filterSeverity)
    );
  }

  getSeverityClass(severity: string): string {
    switch (severity?.toUpperCase()) {
      case 'HIGH': return 'badge-danger';
      case 'MEDIUM': return 'badge-warning';
      case 'LOW': return 'badge-success';
      default: return 'badge-info';
    }
  }

  getSeverityBarClass(severity: string): string {
    switch (severity?.toUpperCase()) {
      case 'HIGH': return 'bar-red';
      case 'MEDIUM': return 'bar-amber';
      case 'LOW': return 'bar-emerald';
      default: return 'bar-blue';
    }
  }

  getComplianceClass(status: string): string {
    switch (status?.toLowerCase()) {
      case 'compliant': return 'badge-success';
      case 'warning': return 'badge-warning';
      case 'violation': return 'badge-danger';
      default: return 'badge-info';
    }
  }
}
