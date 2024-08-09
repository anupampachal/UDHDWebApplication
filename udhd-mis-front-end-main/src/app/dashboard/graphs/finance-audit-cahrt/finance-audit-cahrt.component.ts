import { Component, Input, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { MainIncomeExpenseDTO } from 'src/app/deas/data-views/income-expense/model/main-income-expense-dto';

@Component({
  selector: 'app-finance-audit-cahrt',
  templateUrl: './finance-audit-cahrt.component.html',
  styleUrls: ['./finance-audit-cahrt.component.css']
})
export class FinanceAuditCahrtComponent implements OnInit {
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
      // this.currentPeriod = this.dataToDisplay.currentPeriod
      // this.prevPeriod = this.dataToDisplay.previousPeriod
    }
  }

  barChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false
    }
  };
  barChartLabels: Label[] = []

  // barChartLabels: Label[] = ['Current Year Income', 'Previous Year Income', 'Current Year Expense', 'Previous Year Expense']
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [

  ];

  barChartData: ChartDataSets[] = []

  constructor() { }

  ngOnInit(): void {


  }
}
