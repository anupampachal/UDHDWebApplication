import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
@Component({
  selector: 'app-cag-pac-status-chart',
  templateUrl: './cag-pac-status-chart.component.html',
  styleUrls: ['./cag-pac-status-chart.component.css']
})
export class CagPacStatusChartComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

  }

  public barChartLabels: Label[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' }
  ];


}
