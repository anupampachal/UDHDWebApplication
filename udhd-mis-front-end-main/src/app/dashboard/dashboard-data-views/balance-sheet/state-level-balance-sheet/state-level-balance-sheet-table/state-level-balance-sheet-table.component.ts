import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/dashboard/models/balance-sheet-dto';

@Component({
  selector: 'app-state-level-balance-sheet-table',
  templateUrl: './state-level-balance-sheet-table.component.html',
  styleUrls: ['./state-level-balance-sheet-table.component.css']
})
export class StateLevelBalanceSheetTableComponent implements OnInit {

  dataToDisplay!: BalanceSheetDTO
  @Input() set mainIncomeExpense(value: any) {
    this.dataToDisplay = value;
  }

  constructor() {

   }

  ngOnInit(): void {
  }

}
