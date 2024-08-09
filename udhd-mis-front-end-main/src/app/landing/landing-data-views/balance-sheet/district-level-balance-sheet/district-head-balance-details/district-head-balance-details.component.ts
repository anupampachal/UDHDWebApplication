import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/landing/models/balance-sheet-dto';

@Component({
  selector: 'app-district-head-balance-details',
  templateUrl: './district-head-balance-details.component.html',
  styleUrls: ['./district-head-balance-details.component.css']
})
export class DistrictHeadBalanceDetailsComponent implements OnInit {

  detailsData!: BalanceSheetDTO;

  @Input() set bsDetails(value: any) {
    this.detailsData = value;
  }

  constructor() { }

  ngOnInit(): void {
  }

}
