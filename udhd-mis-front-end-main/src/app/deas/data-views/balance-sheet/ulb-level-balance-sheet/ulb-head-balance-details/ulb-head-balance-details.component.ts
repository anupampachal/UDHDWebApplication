import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/deas/models/balance-sheet-dto';
import { headLevelBalanceSheetDetailsDTO } from 'src/app/deas/models/headLevelBalanceSheetDTO';

@Component({
  selector: 'app-ulb-head-balance-details',
  templateUrl: './ulb-head-balance-details.component.html',
  styleUrls: ['./ulb-head-balance-details.component.css']
})
export class UlbHeadBalanceDetailsComponent implements OnInit {
  detailsData!: headLevelBalanceSheetDetailsDTO[];

  @Input() set bsDetails(value: any) {
    this.detailsData = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
