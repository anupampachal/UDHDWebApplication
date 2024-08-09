import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/dashboard/models/cash-bank-balance-curr-level-dto';

@Component({
  selector: 'app-division-cash-bank-balance-table',
  templateUrl: './division-cash-bank-balance-table.component.html',
  styleUrls: ['./division-cash-bank-balance-table.component.css']
})
export class DivisionCashBankBalanceTableComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO
  @Input() set mainIncomeExpense(value: any) {

    this.dataToDisplay = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
