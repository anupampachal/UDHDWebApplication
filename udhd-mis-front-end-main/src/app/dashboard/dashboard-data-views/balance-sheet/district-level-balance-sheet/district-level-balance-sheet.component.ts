import { Component, OnInit } from '@angular/core';
import { combineLatest, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { BalanceSheetService } from 'src/app/dashboard/services/balance-sheet.service';
import { RouteService } from 'src/app/dashboard/services/route.service';

@Component({
  selector: 'app-district-level-balance-sheet',
  templateUrl: './district-level-balance-sheet.component.html',
  styleUrls: ['./district-level-balance-sheet.component.css']
})
export class DistrictLevelBalanceSheetComponent implements OnInit {
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
        if(currentGeography.level == 'district') {
        return this.service.getDistrictLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.childLevelBalanceSheetData = data.ulbLevelBalanceSheetIndividual,
              this.balanceSheetComparativeData = data.districtLevelBalanceSheet
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
