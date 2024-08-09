import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { BalanceSheetService } from 'src/app/dashboard/services/balance-sheet.service';
import { RouteService } from 'src/app/dashboard/services/route.service';

@Component({
  selector: 'app-state-level-balance-sheet',
  templateUrl: './state-level-balance-sheet.component.html',
  styleUrls: ['./state-level-balance-sheet.component.css']
})
export class StateLevelBalanceSheetComponent implements OnInit {
  stateLevelData$!: Observable<any>
  childLevelBalanceSheetData = []
  balanceSheetHeadDetails = []
  balanceSheetComparativeData = []
  constructor(
    private routeService: RouteService,
    private service: BalanceSheetService
  ) {
  }

  ngOnInit(): void {

    this.routeService.date$.pipe(
      switchMap(currentDate =>
        this.service.getStateLevelIncomeExpense(currentDate)),
      tap((data:any) => {
        this.childLevelBalanceSheetData = data.divisionLevelBalanceSheet,
        this.balanceSheetComparativeData = data.stateLevelBalanceSheet
        this.balanceSheetHeadDetails = data.majorHeadLevelBalanceDetails
      })
    ).subscribe()
  }
}

