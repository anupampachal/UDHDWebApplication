import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';

@Component({
  selector: 'app-division-head-balance-details',
  templateUrl: './division-head-balance-details.component.html',
  styleUrls: ['./division-head-balance-details.component.css']
})
export class DivisionHeadBalanceDetailsComponent implements OnInit {

  detailsData!: BalanceSheetDTO;

  @Input() set bsDetails(value: any) {
    this.detailsData = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
