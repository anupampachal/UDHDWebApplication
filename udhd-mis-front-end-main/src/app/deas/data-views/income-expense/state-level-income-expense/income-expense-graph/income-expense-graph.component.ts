import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
// import { Chart, ChartDataSets, Point } from "chart.js";
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { MainIncomeExpenseDTO } from '../../model/main-income-expense-dto';

@Component({
  selector: 'app-income-expense-graph',
  templateUrl: './income-expense-graph.component.html',
  styleUrls: ['./income-expense-graph.component.css']
})
export class IncomeExpenseGraphComponent implements OnInit {
  currentPeriod!: string;
  prevPeriod!: string;
  dataToDisplay!: MainIncomeExpenseDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
    if (this.dataToDisplay) {
      // this.barChartLabels = ['income'+ this.dataToDisplay.currentPeriod, 'income' + this.dataToDisplay.previousPeriod, '', 'expense' + this.dataToDisplay.currentPeriod, 'expense' + this.dataToDisplay.previousPeriod]
      this.barChartData = [
        {
          data: [
            this.dataToDisplay.incomeAmtFromCurrentToPrev >= 0 ? this.dataToDisplay.incomeAmtFromCurrentToPrev: this.dataToDisplay.incomeAmtFromCurrentToPrev * -1,
            this.dataToDisplay.incomeAmtFromPrevToLast >=0 ? this.dataToDisplay.incomeAmtFromPrevToLast: this.dataToDisplay.incomeAmtFromPrevToLast * -1,
            this.dataToDisplay.expenseAmtFromCurrentToPrev >=0 ? this.dataToDisplay.expenseAmtFromCurrentToPrev: this.dataToDisplay.expenseAmtFromCurrentToPrev * -1,
            this.dataToDisplay.expenseAmtFromPrevToLast >=0 ? this.dataToDisplay.expenseAmtFromPrevToLast: this.dataToDisplay.expenseAmtFromPrevToLast * -1
          ],

          backgroundColor: [
            '#26734d',
            '#66cc99',
            '#24478f',
            '#99b3e6',
          ],
          borderWidth: 1
        },
      ];
      this.currentPeriod = this.dataToDisplay.currentPeriod
      this.prevPeriod = this.dataToDisplay.previousPeriod
    }
  }

  barChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false
    }
  };
  barChartLabels: Label[] = ['Current Year Income', 'Previous Year Income', 'Current Year Expense', 'Previous Year Expense']
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [

  ];

  barChartData: ChartDataSets[] = []

  constructor() { }

  ngOnInit(): void {


  }












  // @ViewChild('chart') private chartRef!: ElementRef;
  // private data1!: Point[];
  // private data2!: Point[];
  // public lineChartData: ChartDataSets[] = [
  //   { data: [], label: '', backgroundColor: '' },
  //   { data: [], label: '', yAxisID: 'y-axis-right' },
  // ];

  // chart!: Chart;

  // constructor() {
  //   this.data1 = [{ x: 1, y: 5 }, { x: 2, y: 10 }, { x: 3, y: 6 }, { x: 4, y: 2 }, { x: 5.1, y: 6 }];
  //   this.data2 = [{ x: 6.1, y: 4 }, { x: 5.5, y: 3 }, { x: 4, y: 6 }, { x: 3.2, y: 10 }, { x: 1, y: 5 },];

  //   this.lineChartData = [
  //     { data: this.data1, label: 'Income', backgroundColor: '#aed6f1', },
  //     { data: this.data2, label: 'Expenditure', backgroundColor: 'pink', },
  //   ]
  //  }
  //  ngAfterViewInit(): void {
  //   this.chart = new Chart(this.chartRef.nativeElement, {
  //     type: 'line',
  //     data: {
  //       datasets: this.lineChartData
  //     },
  //     options: {
  //       responsive: true,
  //       scales: {
  //         xAxes: [{
  //           labels: ["2020-21", "2021-22"]
  //         }],
  //         yAxes: [
  //           {
  //             id: 'y-axis-left',
  //             position: 'left',
  //             gridLines: {
  //               color: 'rgb(168, 41, 37)',
  //             },
  //             ticks: {
  //               suggestedMax: 10,
  //               suggestedMin: -10,
  //               stepSize: 2,
  //               fontColor: 'rgb(25, 219, 233)',
  //             }
  //           },
  //           {
  //             id: 'y-axis-right',
  //             position: 'right',
  //             gridLines: {
  //               color: 'rgba(212, 141, 33, 0.3)',
  //             },
  //             ticks: {
  //               suggestedMax: 10,
  //               suggestedMin: -10,
  //               stepSize: 2,
  //               fontColor: 'rgb(230, 23, 133)',
  //             }
  //           }
  //         ]
  //       },

  //     },

  //   });
  //   this.chart.render()
  // }


}
