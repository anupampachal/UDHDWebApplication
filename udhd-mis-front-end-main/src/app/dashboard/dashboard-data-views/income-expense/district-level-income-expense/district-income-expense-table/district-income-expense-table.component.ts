import { Component, Input, OnInit } from '@angular/core';
import { MainIncomeExpenseDTO } from '../../model/main-income-expense-dto';

@Component({
  selector: 'app-district-income-expense-table',
  templateUrl: './district-income-expense-table.component.html',
  styleUrls: ['./district-income-expense-table.component.css']
})
export class DistrictIncomeExpenseTableComponent implements OnInit {
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
