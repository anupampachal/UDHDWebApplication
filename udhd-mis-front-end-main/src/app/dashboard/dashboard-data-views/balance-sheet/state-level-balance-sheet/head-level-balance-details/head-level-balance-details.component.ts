import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';

@Component({
  selector: 'app-head-level-balance-details',
  templateUrl: './head-level-balance-details.component.html',
  styleUrls: ['./head-level-balance-details.component.css']
})
export class HeadLevelBalanceDetailsComponent implements OnInit {

  detailsData!: BalanceSheetDTO;

  @Input() set bsDetails(value: any) {
    this.detailsData = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
