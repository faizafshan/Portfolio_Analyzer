import { Component, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { AppStateService } from '../../services/app-state.service';
import { Portfolio, OptimizationResult } from '../../models/portfolio.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-simulator',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './simulator.component.html',
  styleUrl: './simulator.component.scss'
})
export class SimulatorComponent implements OnDestroy {
  portfolios: Portfolio[] = [];
  result: OptimizationResult | null = null;
  strategy = 'balanced';
  loading = false;
  private chart: Chart | null = null;

  @ViewChild('comparisonCanvas') comparisonCanvas!: ElementRef<HTMLCanvasElement>;

  constructor(public state: AppStateService, private api: ApiService) {
    this.api.getPortfolios().subscribe(p => this.portfolios = p);
  }

  ngOnDestroy(): void {
    this.chart?.destroy();
  }

  runSimulation(): void {
    this.loading = true;
    this.result = null;
    this.api.optimize(this.state.selectedPortfolioId(), this.strategy).subscribe({
      next: (r) => {
        this.result = r;
        this.loading = false;
        setTimeout(() => this.createChart(), 100);
      },
      error: () => this.loading = false
    });
  }

  get comparisonData() {
    if (!this.result) return [];
    return [
      { metric: 'Expected Return', before: this.result.currentReturn, after: this.result.projectedReturn },
      { metric: 'Risk Score', before: this.result.currentRisk, after: this.result.projectedRisk },
      { metric: 'Sharpe Ratio', before: this.result.currentSharpe, after: this.result.projectedSharpe },
      { metric: 'Volatility', before: this.result.currentVolatility, after: this.result.projectedVolatility },
    ];
  }

  isImproved(item: any): boolean {
    if (item.metric === 'Risk Score' || item.metric === 'Volatility') return item.after < item.before;
    return item.after > item.before;
  }

  getDeltaPct(item: any): number {
    return Math.abs(((item.after - item.before) / item.before) * 100);
  }

  private createChart(): void {
    this.chart?.destroy();
    const ctx = this.comparisonCanvas?.nativeElement?.getContext('2d');
    if (!ctx || !this.result) return;

    const data = this.comparisonData;
    this.chart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.map(d => d.metric),
        datasets: [
          { label: 'Before', data: data.map(d => d.before), backgroundColor: 'rgba(239, 68, 68, 0.5)', borderRadius: 4 },
          { label: 'After', data: data.map(d => d.after), backgroundColor: 'rgba(16, 185, 129, 0.7)', borderRadius: 4 },
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { labels: { color: '#94a3b8', font: { size: 11 } } } },
        scales: {
          x: { ticks: { color: '#64748b', font: { size: 10 } }, grid: { color: 'rgba(56,78,119,0.15)' } },
          y: { ticks: { color: '#64748b', font: { size: 10 } }, grid: { color: 'rgba(56,78,119,0.15)' } }
        }
      }
    });
  }

  getActionClass(action: string): string {
    switch (action?.toLowerCase()) {
      case 'buy': return 'badge-success';
      case 'sell': return 'badge-danger';
      case 'hold': return 'badge-warning';
      default: return 'badge-info';
    }
  }
}
