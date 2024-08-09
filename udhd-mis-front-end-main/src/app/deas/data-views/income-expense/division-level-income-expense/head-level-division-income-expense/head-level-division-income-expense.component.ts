import { Component, Input, OnInit } from '@angular/core';
import { HeadLevelIncomeExpenseDTO } from '../../model/head-level-income-expense-dto';

@Component({
  selector: 'app-head-level-division-income-expense',
  templateUrl: './head-level-division-income-expense.component.html',
  styleUrls: ['./head-level-division-income-expense.component.css']
})
export class HeadLevelDivisionIncomeExpenseComponent implements OnInit {

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
