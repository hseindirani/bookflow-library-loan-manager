import { Component } from '@angular/core';
import { DashboardService } from './dashboard.service';
import { DashboardResponse } from './dashboard.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  memberId = 1;
  dashboard: DashboardResponse | null = null;

  constructor(private dashboardService: DashboardService) {}
  loadDashboard(): void {
  console.log('memberId:', this.memberId);

  this.dashboardService.getDashboard(this.memberId).subscribe({
    next: response => {
      console.log('dashboard response:', response);
      this.dashboard = response;
    },
    error: error => {
      console.error('dashboard error:', error);
    }
  });
}
}



