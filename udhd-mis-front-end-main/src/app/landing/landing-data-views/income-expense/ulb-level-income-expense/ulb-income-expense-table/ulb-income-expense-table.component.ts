import { Component, Input, OnInit } from '@angular/core';
import { MainIncomeExpenseDTO } from '../../model/main-income-expense-dto';

@Component({
  selector: 'app-ulb-income-expense-table',
  templateUrl: './ulb-income-expense-table.component.html',
  styleUrls: ['./ulb-income-expense-table.component.css']
})
export class UlbIncomeExpenseTableComponent implements OnInit {
  currentPeriod!: string;
  prevPeriod!: string;
  dataToDisplay!: MainIncomeExpenseDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
    if (this.dataToDisplay) {
      this.currentPeriod = this.dataToDisplay.currentPeriod
      this.prevPeriod = this.dataToDisplay.previousPeriod
    }
  }

  constructor() {

   }

  ngOnInit(): void {
  }

}
