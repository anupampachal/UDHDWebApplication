import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardDTO } from '../models/dashboard-chart-dto';
import { GraphService } from '../services/graph.service';

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css']
})
export class GraphsComponent implements OnInit {
  dashboardDTO$!:Observable<DashboardDTO>;
  constructor(private graphService:GraphService) { }

  ngOnInit(): void {
    this.dashboardDTO$=this.graphService.getDashboardData();
  }
  deas() {

  }
  financeAudit() {

  }
  internalAudit() {

  }
  agAudit() {

  }
}
