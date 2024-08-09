import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceDetailsDTO } from 'src/app/dashboard/models/cash-bank-balance-details-dto';

@Component({
  selector: 'app-head-level-ulb-cash-bank-balance',
  templateUrl: './head-level-ulb-cash-bank-balance.component.html',
  styleUrls: ['./head-level-ulb-cash-bank-balance.component.css']
})
export class HeadLevelUlbCashBankBalanceComponent implements OnInit {
  detailsData!: CashBankBalanceDetailsDTO;

  @Input() set cbbDetails(value: any) {
    this.detailsData = value;
  }
  constructor() { }

  ngOnInit(): void {
  }

}
