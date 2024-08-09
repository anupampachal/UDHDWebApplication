import { Component, Input, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { MainIncomeExpenseDTO } from 'src/app/deas/data-views/income-expense/model/main-income-expense-dto';

@Component({
  selector: 'app-ag-audit-cahrt',
  templateUrl: './ag-audit-cahrt.component.html',
  styleUrls: ['./ag-audit-cahrt.component.css']
})
export class AgAuditCahrtComponent implements OnInit {
  dataToDisplay!: MainIncomeExpenseDTO
  // @Input() set mainIncomeExpense(value: any) {
  //   this.dataToDisplay = value;
  //   
  //   if (this.dataToDisplay) {
  //     // this.barChartLabels = ['income'+ this.dataToDisplay.currentPeriod, 'income' + this.dataToDisplay.previousPeriod, '', 'expense' + this.dataToDisplay.currentPeriod, 'expense' + this.dataToDisplay.previousPeriod]
  //     this.barChartData = [
  //       {
  //         data: [
  //           this.dataToDisplay.incomeAmtFromCurrentToPrev >= 0 ? this.dataToDisplay.incomeAmtFromCurrentToPrev: this.dataToDisplay.incomeAmtFromCurrentToPrev * -1,
  //           this.dataToDisplay.incomeAmtFromPrevToLast >=0 ? this.dataToDisplay.incomeAmtFromPrevToLast: this.dataToDisplay.incomeAmtFromPrevToLast * -1,
  //           this.dataToDisplay.expenseAmtFromCurrentToPrev >=0 ? this.dataToDisplay.expenseAmtFromCurrentToPrev: this.dataToDisplay.expenseAmtFromCurrentToPrev * -1,
  //           this.dataToDisplay.expenseAmtFromPrevToLast >=0 ? this.dataToDisplay.expenseAmtFromPrevToLast: this.dataToDisplay.expenseAmtFromPrevToLast * -1
  //         ],

  //         backgroundColor: [
  //           '#26734d',
  //           '#66cc99',
  //           '#24478f',
  //           '#99b3e6',
  //         ],
  //         borderWidth: 1
  //       },
  //     ];
  //     // this.currentPeriod = this.dataToDisplay.currentPeriod
  //     // this.prevPeriod = this.dataToDisplay.previousPeriod
  //   }
  // }
/*
  barChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false
    }
  };
  barChartLabels: Label[] = ["1", "2", "3", "4"]

  // barChartLabels: Label[] = ['Current Year Income', 'Previous Year Income', 'Current Year Expense', 'Previous Year Expense']
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [

  ];

  barChartData: ChartDataSets[] = [{
    data: [10, 12, 16, 19],
    // label: 'A Bar Chart',
    backgroundColor: ['#808080', '#808080', '#808080', '#808080']
  }];

  constructor() { }

  ngOnInit(): void {


  }*/
ngOnInit(): void {
    
}
  title = 'Bar Chart Example in Angular 4';

  // ADD CHART OPTIONS. 
  chartOptions = {
    responsive: true    // THIS WILL MAKE THE CHART RESPONSIVE (VISIBLE IN ANY DEVICE).
  }
  barChartType: ChartType = 'bar';
  labels =  ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];

  // STATIC DATA FOR THE CHART IN JSON FORMAT.
  chartData = [
    {
      label: '1st Year',
      data: [21, 56, 4, 31, 45, 15, 57, 61, 9, 17, 24, 59] 
    },
    { 
      label: '2nd Year',
      data: [47, 9, 28, 54, 77, 51, 24]
    }
  ];

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
  onChartClick(event:any) {
    
  }
}
