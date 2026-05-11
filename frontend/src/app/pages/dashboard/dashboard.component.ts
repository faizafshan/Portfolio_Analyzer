import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { AppStateService } from '../../services/app-state.service';
import { Portfolio, ExecutiveSummary, AgentStatus, DriftAnalysis } from '../../models/portfolio.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  portfolios: Portfolio[] = [];
  portfolio: Portfolio | null = null;
  summary: ExecutiveSummary | null = null;
  agents: AgentStatus[] = [];
  drift: DriftAnalysis | null = null;

  private allocationChart: Chart | null = null;
  private performanceChart: Chart | null = null;

  @ViewChild('allocationCanvas') allocationCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('performanceCanvas') performanceCanvas!: ElementRef<HTMLCanvasElement>;

  constructor(public state: AppStateService, private api: ApiService) {}

  ngOnInit(): void {
    this.loadData();
  }

  ngAfterViewInit(): void {
    // Charts created after data loads
  }

  ngOnDestroy(): void {
    this.allocationChart?.destroy();
    this.performanceChart?.destroy();
  }

  loadData(): void {
    this.api.getPortfolios().subscribe(p => {
      this.portfolios = p;
    });
    const id = this.state.selectedPortfolioId();
    this.api.getPortfolio(id).subscribe(p => {
      this.portfolio = p;
      setTimeout(() => this.createCharts(), 100);
    });
    this.api.executiveSummary(id).subscribe(s => this.summary = s);
    this.api.agentStatus().subscribe(a => this.agents = a);
    this.api.getDrift(id).subscribe(d => this.drift = d);
  }

  onPortfolioChange(): void {
    this.loadData();
  }

  get kpis() {
    if (!this.summary) return [];
    return [
      { label: 'Health Score', value: this.summary.healthScore, prev: this.summary.previousHealthScore, color: '#3b82f6', icon: 'activity' },
      { label: 'Compliance', value: this.summary.complianceScore, color: '#10b981', icon: 'shield' },
      { label: 'ESG Score', value: this.summary.esgScore, color: '#22c55e', icon: 'leaf' },
      { label: 'Drift Severity', value: this.summary.driftSeverity, color: '#f59e0b', icon: 'target', subtitle: 'Lower is better' },
    ];
  }

  get secondaryKpis() {
    if (!this.summary) return [];
    return [
      { label: 'Optimization ROI', value: this.summary.optimizationROI, color: '#8b5cf6', suffix: '%' },
      { label: 'Urgent Alerts', value: this.summary.urgentAlerts, color: '#ef4444' },
      { label: 'Pending Actions', value: this.summary.pendingRecommendations, color: '#06b6d4' },
    ];
  }

  private createCharts(): void {
    if (!this.portfolio) return;

    // Allocation Chart
    this.allocationChart?.destroy();
    const allocCtx = this.allocationCanvas?.nativeElement?.getContext('2d');
    if (allocCtx) {
      const labels = this.portfolio.holdings.map(h => h.symbol);
      this.allocationChart = new Chart(allocCtx, {
        type: 'bar',
        data: {
          labels,
          datasets: [
            { label: 'Current %', data: this.portfolio.holdings.map(h => h.weight), backgroundColor: 'rgba(59, 130, 246, 0.6)', borderRadius: 4 },
            { label: 'Target %', data: this.portfolio.holdings.map(h => h.targetWeight), backgroundColor: 'rgba(16, 185, 129, 0.4)', borderRadius: 4 },
          ]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: { legend: { labels: { color: '#94a3b8', font: { size: 11 } } } },
          scales: {
            x: { ticks: { color: '#64748b', font: { size: 10 } }, grid: { color: 'rgba(56, 78, 119, 0.15)' } },
            y: { ticks: { color: '#64748b', font: { size: 10 } }, grid: { color: 'rgba(56, 78, 119, 0.15)' } }
          }
        }
      });
    }

    // Performance Donut
    this.performanceChart?.destroy();
    const perfCtx = this.performanceCanvas?.nativeElement?.getContext('2d');
    if (perfCtx) {
      const sectors = [...new Set(this.portfolio.holdings.map(h => h.sector))];
      const sectorWeights = sectors.map(s => this.portfolio!.holdings.filter(h => h.sector === s).reduce((acc, h) => acc + h.weight, 0));
      const colors = ['#3b82f6', '#10b981', '#f59e0b', '#8b5cf6', '#ef4444', '#06b6d4', '#f97316', '#ec4899'];
      this.performanceChart = new Chart(perfCtx, {
        type: 'doughnut',
        data: {
          labels: sectors,
          datasets: [{ data: sectorWeights, backgroundColor: colors.slice(0, sectors.length), borderWidth: 0, spacing: 2 }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          cutout: '65%',
          plugins: { legend: { position: 'right', labels: { color: '#94a3b8', font: { size: 11 }, padding: 12, usePointStyle: true, pointStyle: 'circle' } } }
        }
      });
    }
  }

  getAgentStatusClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'active': return 'badge-success';
      case 'idle': return 'badge-info';
      case 'warning': return 'badge-warning';
      default: return 'badge-info';
    }
  }

  getDelta(kpi: any): number | null {
    if (kpi.prev == null) return null;
    return kpi.value - kpi.prev;
  }
}
