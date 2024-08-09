import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/deas/models/cash-bank-balance-curr-level-dto';

@Component({
  selector: 'app-ulb-cash-bank-balance-table',
  templateUrl: './ulb-cash-bank-balance-table.component.html',
  styleUrls: ['./ulb-cash-bank-balance-table.component.css']
})
export class UlbCashBankBalanceTableComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO
  @Input() set mainIncomeExpense(value: any) {

    this.dataToDisplay = value;
  }
  constructor() { }

  ngOnInit(): void {
  }

}
