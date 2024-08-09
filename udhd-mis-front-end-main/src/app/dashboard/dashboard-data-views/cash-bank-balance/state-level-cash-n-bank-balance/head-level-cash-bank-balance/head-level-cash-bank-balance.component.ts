import { Component, Input, OnInit } from '@angular/core';
import { CashBankBalanceDetailsDTO } from 'src/app/dashboard/models/cash-bank-balance-details-dto';
// import { CashBankBalanceDetailsDTO } from 'src/app/dashboard/models/cash-bank-balance-details-dto';

@Component({
  selector: 'app-head-level-cash-bank-balance',
  templateUrl: './head-level-cash-bank-balance.component.html',
  styleUrls: ['./head-level-cash-bank-balance.component.css']
})
export class HeadLevelCashBankBalanceComponent implements OnInit {

  detailsData!: CashBankBalanceDetailsDTO;

  @Input() set cbbDetails(value: any) {
    this.detailsData = value;
  }



  constructor() { }

  ngOnInit(): void {
  }

}
