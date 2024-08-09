import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/landing/models/cash-bank-balance-curr-level-dto';

@Component({
  selector: 'app-state-cash-bank-balance-table',
  templateUrl: './state-cash-bank-balance-table.component.html',
  styleUrls: ['./state-cash-bank-balance-table.component.css']
})
export class StateCashBankBalanceTableComponent implements OnInit {

  currentPeriod!: string;
  prevPeriod!: string;
  dataToDisplay!: CashBankBalanceCurrLevelDTO
  @Input() set mainIncomeExpense(value: any) {

    this.dataToDisplay = value;
  }

  constructor() {

   }

  ngOnInit(): void {
  }

}
