import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceCurrLevelDTO } from 'src/app/dashboard/models/cash-bank-balance-curr-level-dto';

@Component({
  selector: 'app-district-cashn-bank-balance-table',
  templateUrl: './district-cashn-bank-balance-table.component.html',
  styleUrls: ['./district-cashn-bank-balance-table.component.css']
})
export class DistrictCashnBankBalanceTableComponent implements OnInit {

  dataToDisplay!: CashBankBalanceCurrLevelDTO
  @Input() set mainIncomeExpense(value: any) {

    this.dataToDisplay = value;
  }

  constructor() { }

  ngOnInit(): void {
  }
}
