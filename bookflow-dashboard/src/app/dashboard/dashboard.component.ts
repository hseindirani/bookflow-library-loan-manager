import { Component } from '@angular/core';
import { DashboardService } from './dashboard.service';
import { DashboardResponse } from './dashboard.model';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule,DatePipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  memberId = 1;
  dashboard: DashboardResponse | null = null;
  errorMessage: string | null = null;

  constructor(private dashboardService: DashboardService) {}
  loadDashboard(): void {
  this.errorMessage = null;

  this.dashboardService.getDashboard(this.memberId).subscribe({
    next: response => {
      this.dashboard = response;
    },
    error: error => {
      this.dashboard = null;

      if (error.status === 404) {
        this.errorMessage = 'Member not found.';
      } else {
        this.errorMessage = 'Unable to load dashboard.';
      }
    }
  });
}
}



