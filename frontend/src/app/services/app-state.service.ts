import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AppStateService {
  readonly selectedPortfolioId = signal('PF-001');
  readonly sidebarCollapsed = signal(false);

  setPortfolio(id: string): void {
    this.selectedPortfolioId.set(id);
  }

  toggleSidebar(): void {
    this.sidebarCollapsed.update(v => !v);
  }
}
