import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';

@Component({
  selector: 'app-division-level-balance-sheet-table',
  templateUrl: './division-level-balance-sheet-table.component.html',
  styleUrls: ['./division-level-balance-sheet-table.component.css']
})
export class DivisionLevelBalanceSheetTableComponent implements OnInit {

  dataToDisplay!: BalanceSheetDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
  }

  constructor() {

   }

  ngOnInit(): void {
  }

}
