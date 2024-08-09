import { Component, Input, OnInit } from '@angular/core';
import { HeadLevelIncomeExpenseDTO } from '../../model/head-level-income-expense-dto';

@Component({
  selector: 'app-head-level-income-expense',
  templateUrl: './head-level-income-expense.component.html',
  styleUrls: ['./head-level-income-expense.component.css']
})
export class HeadLevelIncomeExpenseComponent implements OnInit {

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
