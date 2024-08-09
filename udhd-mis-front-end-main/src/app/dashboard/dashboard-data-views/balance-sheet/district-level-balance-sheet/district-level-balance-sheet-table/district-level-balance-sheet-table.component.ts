import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';

@Component({
  selector: 'app-district-level-balance-sheet-table',
  templateUrl: './district-level-balance-sheet-table.component.html',
  styleUrls: ['./district-level-balance-sheet-table.component.css']
})
export class DistrictLevelBalanceSheetTableComponent implements OnInit {

  dataToDisplay!: BalanceSheetDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
  }
  constructor() { }

  ngOnInit(): void {
  }

}
