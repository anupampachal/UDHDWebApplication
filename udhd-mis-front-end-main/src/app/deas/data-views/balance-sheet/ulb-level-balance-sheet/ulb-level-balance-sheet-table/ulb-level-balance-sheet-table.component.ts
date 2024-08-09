import { Component, Input, OnInit } from '@angular/core';
import { BalanceSheetDTO } from 'src/app/deas/models/balance-sheet-dto';

@Component({
  selector: 'app-ulb-level-balance-sheet-table',
  templateUrl: './ulb-level-balance-sheet-table.component.html',
  styleUrls: ['./ulb-level-balance-sheet-table.component.css']
})
export class UlbLevelBalanceSheetTableComponent implements OnInit {
  dataToDisplay!: BalanceSheetDTO
  @Input() set mainIncomeExpense(value: any) {
    
    this.dataToDisplay = value;
  }
  constructor() { }

  ngOnInit(): void {
  }

}
