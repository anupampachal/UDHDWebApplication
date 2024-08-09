import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { MainIncomeExpenseDTO } from '../../model/main-income-expense-dto';


@Component({
  selector: 'app-ulb-income-expense-graph',
  templateUrl: './ulb-income-expense-graph.component.html',
  styleUrls: ['./ulb-income-expense-graph.component.css']
})
export class UlbIncomeExpenseGraphComponent implements OnInit {
  currentPeriod!: string;
  prevPeriod!: string;
  dataToDisplay!: MainIncomeExpenseDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
    if (this.dataToDisplay) {
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

  // barChartLabels: Label[] = ['income', 'expense', '', 'income', 'expense']
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [

  ];

  barChartData: ChartDataSets[] = []

  constructor() { }

  ngOnInit(): void {


  }
}
