import { CurrencyPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { MainIncomeExpenseDTO } from '../../model/main-income-expense-dto';

@Component({
  selector: 'app-income-expense-table',
  templateUrl: './income-expense-table.component.html',
  styleUrls: ['./income-expense-table.component.css']
})
export class IncomeExpenseTableComponent implements OnInit {
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

  constructor(private currencyPipe:CurrencyPipe) { }


  ngOnInit(): void { }

  // getCurrencyAsNeeded(amount:number){
  //   if(amount==0)
  //    return 0;

  //   let multiplicand=1;
  //   if(amount<0) {
  //     multiplicand=-1;
  //   }
  //   const amountc= this.currencyPipe.transform(amount*multiplicand,'INR','₹','3.2-2')
  //   return multiplicand>0? amountc+' Cr.': amountc+' Dr.';

  // }
}
