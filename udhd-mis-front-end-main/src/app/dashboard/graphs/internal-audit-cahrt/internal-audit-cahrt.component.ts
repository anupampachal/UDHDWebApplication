import { Component, Input, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { MainIncomeExpenseDTO } from 'src/app/deas/data-views/income-expense/model/main-income-expense-dto';
import { IAAuditDashboardDTO } from '../../models/ia-chart-dto';
import { GraphService } from '../../services/graph.service';

@Component({
  selector: 'app-internal-audit-cahrt',
  templateUrl: './internal-audit-cahrt.component.html',
  styleUrls: ['./internal-audit-cahrt.component.css']
})
export class InternalAuditCahrtComponent implements OnInit {
  @Input() data!: IAAuditDashboardDTO;
  constructor(private graphService: GraphService) { }
  dataToDisplay!: MainIncomeExpenseDTO;
  chartData!: any;
  labels = [];
  barChartType: ChartType = 'bar';
  title = 'Bar Chart Example in Angular 4';
  // ADD CHART OPTIONS. 
  chartOptions = {
    responsive: true    // THIS WILL MAKE THE CHART RESPONSIVE (VISIBLE IN ANY DEVICE).
  }

  ngOnInit(): void {
    
    this.chartData = [
      {
        label: this.data.currentPeriod,
        data: [this.data.currentData]
      }, {
        label: this.data.previousPeriod,
        data: [this.data.previousData]

      }]

  }

  // CHART COLOR.
  colors = [
    { // 1st Year.
      backgroundColor: 'rgba(77,83,96,0.2)'
    },
    { // 2nd Year.
      backgroundColor: 'rgba(30, 169, 224, 0.8)'
    }
  ]

  // CHART CLICK EVENT.
  onChartClick(event: any) {
    
  }
}
