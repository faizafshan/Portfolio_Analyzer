import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { ParsedConstraints } from '../../models/portfolio.model';

@Component({
  selector: 'app-constraint-intelligence',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './constraint-intelligence.component.html',
  styleUrl: './constraint-intelligence.component.scss'
})
export class ConstraintIntelligenceComponent {
  policyText = 'Client prefers ESG aligned investments with no tobacco or weapons exposure. Conservative risk profile with maximum 8% single stock allocation and 10% maximum drawdown tolerance. Require minimum 20% fixed income allocation with emphasis on capital preservation and dividend income.';
  result: ParsedConstraints | null = null;
  loading = false;

  templates = [
    'Client prefers ESG aligned investments with no tobacco or weapons exposure. Conservative risk profile with maximum 8% single stock allocation and 10% maximum drawdown tolerance.',
    'Aggressive growth strategy with up to 15% single position. No sector restrictions. Maximum drawdown 25%. Focus on technology and healthcare sectors.',
    'Balanced approach: 60/40 equity-fixed income split. No fossil fuel or gambling exposure. Max 5% per position. Minimum 30% international diversification.'
  ];

  constructor(private api: ApiService) {}

  parseConstraints(): void {
    this.loading = true;
    this.api.parseConstraints(this.policyText).subscribe({
      next: (r) => { this.result = r; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  useTemplate(idx: number): void {
    this.policyText = this.templates[idx];
    this.result = null;
  }
}
