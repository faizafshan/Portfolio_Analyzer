import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { Portfolio, DriftAnalysis, ComplianceCheckResult, EsgAnalysis } from '../../models/portfolio.model';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-portfolio-analysis',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './portfolio-analysis.component.html',
  styleUrl: './portfolio-analysis.component.scss'
})
export class PortfolioAnalysisComponent implements OnInit, OnDestroy {
  Math = Math;
  portfolio: Portfolio | null = null;
  drift: DriftAnalysis | null = null;
  compliance: ComplianceCheckResult | null = null;
  esg: EsgAnalysis | null = null;

  private radarChart: Chart | null = null;
  private sectorChart: Chart | null = null;

  @ViewChild('radarCanvas') radarCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('sectorCanvas') sectorCanvas!: ElementRef<HTMLCanvasElement>;

  constructor(private route: ActivatedRoute, private api: ApiService) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id') || 'PF-001';
    this.api.getPortfolio(id).subscribe(p => {
      this.portfolio = p;
      setTimeout(() => this.createCharts(), 100);
    });
    this.api.getDrift(id).subscribe(d => this.drift = d);
    this.api.complianceCheck(id).subscribe(c => this.compliance = c);
    this.api.esgAnalysis(id).subscribe(e => this.esg = e);
  }

  ngOnDestroy(): void {
    this.radarChart?.destroy();
    this.sectorChart?.destroy();
  }

  private createCharts(): void {
    if (!this.portfolio) return;

    // Radar Chart
    const radarCtx = this.radarCanvas?.nativeElement?.getContext('2d');
    if (radarCtx) {
      this.radarChart = new Chart(radarCtx, {
        type: 'radar',
        data: {
          labels: ['Health', 'Compliance', 'ESG', 'Return', 'Diversification'],
          datasets: [{
            label: 'Portfolio Score',
            data: [this.portfolio.healthScore, this.portfolio.complianceScore, this.portfolio.esgScore,
                   (this.portfolio.actualReturn / this.portfolio.benchmarkReturn) * 100,
                   100 - this.portfolio.driftScore],
            backgroundColor: 'rgba(59, 130, 246, 0.15)',
            borderColor: '#3b82f6',
            borderWidth: 2,
            pointBackgroundColor: '#3b82f6',
            pointRadius: 3
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: { r: { beginAtZero: true, max: 100, ticks: { color: '#64748b', font: { size: 9 } }, grid: { color: 'rgba(56, 78, 119, 0.2)' }, pointLabels: { color: '#94a3b8', font: { size: 11 } } } },
          plugins: { legend: { display: false } }
        }
      });
    }

    // Sector Bar
    const sectorCtx = this.sectorCanvas?.nativeElement?.getContext('2d');
    if (sectorCtx) {
      const sectors = [...new Set(this.portfolio.holdings.map(h => h.sector))];
      const weights = sectors.map(s => this.portfolio!.holdings.filter(h => h.sector === s).reduce((a, h) => a + h.weight, 0));
      this.sectorChart = new Chart(sectorCtx, {
        type: 'bar',
        data: {
          labels: sectors,
          datasets: [{ data: weights, backgroundColor: 'rgba(59, 130, 246, 0.5)', borderRadius: 4 }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          indexAxis: 'y',
          plugins: { legend: { display: false } },
          scales: {
            x: { ticks: { color: '#64748b', font: { size: 10 } }, grid: { color: 'rgba(56, 78, 119, 0.15)' } },
            y: { ticks: { color: '#94a3b8', font: { size: 11 } }, grid: { display: false } }
          }
        }
      });
    }
  }

  getSeverityClass(severity: string): string {
    switch (severity?.toLowerCase()) {
      case 'high': return 'badge-danger';
      case 'medium': return 'badge-warning';
      case 'low': return 'badge-success';
      default: return 'badge-info';
    }
  }

  getComplianceClass(status: string): string {
    switch (status?.toLowerCase()) {
      case 'compliant': case 'pass': return 'badge-success';
      case 'warning': return 'badge-warning';
      case 'violation': case 'fail': return 'badge-danger';
      default: return 'badge-info';
    }
  }
}
