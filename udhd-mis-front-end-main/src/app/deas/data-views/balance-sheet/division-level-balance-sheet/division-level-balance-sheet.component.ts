import { Component, OnInit } from '@angular/core';
import {  combineLatest, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BalanceSheetService } from 'src/app/deas/services/balance-sheet.service';
import { RouteService } from 'src/app/deas/services/route.service';

@Component({
  selector: 'app-division-level-balance-sheet',
  templateUrl: './division-level-balance-sheet.component.html',
  styleUrls: ['./division-level-balance-sheet.component.css']
})
export class DivisionLevelBalanceSheetComponent implements OnInit {
  childLevelBalanceSheetData = []
  balanceSheetHeadDetails = []
  balanceSheetComparativeData = []
  constructor(
    private routeService: RouteService,
    private service: BalanceSheetService
  ) {
  }

  ngOnInit(): void {
    let requierdData$ = combineLatest([
      this.routeService.geography$,
      this.routeService.date$
    ]).pipe(
      map(([currentGeography, CurrentDate]) => {
        if(currentGeography.level == 'division') {
        return this.service.getDivisionLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.childLevelBalanceSheetData = data.districtLevelBalanceSheetIndividual,
              this.balanceSheetComparativeData = data.divisionLevelBalanceSheet
              this.balanceSheetHeadDetails = data.majorHeadLevelBalanceDetails
            })

          )
          .subscribe()
      }
      return of("")
    })
    )
    requierdData$.subscribe()
  }

}
