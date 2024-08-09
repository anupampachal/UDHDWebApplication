import { Component, Input, OnInit } from '@angular/core';
import { HeadLevelIncomeExpenseDTO } from '../../model/head-level-income-expense-dto';

@Component({
  selector: 'app-head-level-district-income-expense',
  templateUrl: './head-level-district-income-expense.component.html',
  styleUrls: ['./head-level-district-income-expense.component.css']
})
export class HeadLevelDistrictIncomeExpenseComponent implements OnInit {


  incomeData!: HeadLevelIncomeExpenseDTO[];
  expenseData!: HeadLevelIncomeExpenseDTO[]

  @Input() set headLevelExpense(value: any) {
    this.expenseData = value;
  }
  @Input() set headLevelIncome(value: any) {
    this.incomeData = value;
  }



  constructor() { }

  ngOnInit(): void {
  }

}
