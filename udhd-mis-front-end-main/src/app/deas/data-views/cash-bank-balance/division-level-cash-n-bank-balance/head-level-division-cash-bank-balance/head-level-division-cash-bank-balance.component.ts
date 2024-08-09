import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceDetailsDTO } from 'src/app/deas/models/cash-bank-balance-details-dto';

@Component({
  selector: 'app-head-level-division-cash-bank-balance',
  templateUrl: './head-level-division-cash-bank-balance.component.html',
  styleUrls: ['./head-level-division-cash-bank-balance.component.css']
})
export class HeadLevelDivisionCashBankBalanceComponent implements OnInit {
  detailsData!: CashBankBalanceDetailsDTO;

  @Input() set cbbDetails(value: any) {
    this.detailsData = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
