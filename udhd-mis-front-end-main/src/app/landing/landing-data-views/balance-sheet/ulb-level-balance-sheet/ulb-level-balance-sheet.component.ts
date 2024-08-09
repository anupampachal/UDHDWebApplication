import { Component, OnInit } from '@angular/core';
import { combineLatest, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BalanceSheetService } from 'src/app/landing/services/balance-sheet.service';
import { RouteService } from 'src/app/landing/services/route.service';

@Component({
  selector: 'app-ulb-level-balance-sheet',
  templateUrl: './ulb-level-balance-sheet.component.html',
  styleUrls: ['./ulb-level-balance-sheet.component.css']
})
export class UlbLevelBalanceSheetComponent implements OnInit {
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
        if(currentGeography.level == 'ulb') {
        return this.service.getUlbLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              
              this.balanceSheetComparativeData = data.ulbLevelBalanceSheet
              this.balanceSheetHeadDetails = data.majorHeadLevelBalanceDetails
            })

          )
          .subscribe()
       }
       return of()
      })
    )
    requierdData$.subscribe()
  }

}
