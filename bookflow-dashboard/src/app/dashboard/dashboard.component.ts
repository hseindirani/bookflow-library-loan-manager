import { Component } from '@angular/core';
import { DashboardService } from './dashboard.service';
import { DashboardResponse } from './dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  dashboard: DashboardResponse | null = null;

  constructor(private dashboardService: DashboardService) {}

}

}