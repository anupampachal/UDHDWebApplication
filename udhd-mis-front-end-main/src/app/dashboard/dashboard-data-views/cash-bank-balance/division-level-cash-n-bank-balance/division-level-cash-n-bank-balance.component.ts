import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, Observable, of } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';
import { CashBankBalanceService } from 'src/app/dashboard/services/cash-bank-balance.service';
import { RouteService } from 'src/app/dashboard/services/route.service';
import { DateRangeDTO } from 'src/app/dashboard/sharedFilters/date-range-dto';

@Component({
  selector: 'app-division-level-cash-n-bank-balance',
  templateUrl: './division-level-cash-n-bank-balance.component.html',
  styleUrls: ['./division-level-cash-n-bank-balance.component.css']
})
export class DivisionLevelCashNBankBalanceComponent implements OnInit {

  routeArray: ActivatedRoute[] = [];
  divisionHierarchy!: any;
  stateLevelData$!: Observable<any>
  currentDateRange!: DateRangeDTO;

  childLevelCashBankBalance = []
  cashBankBalanceDetails = []
  mainCashBankBalanceComparativeData = []
  constructor(
    private route: ActivatedRoute,
    private routeService: RouteService,
    private service: CashBankBalanceService
  ) {
  }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;

    this.routeService.date$.pipe(
      switchMap(currentDate =>
        this.service.getStateLevelIncomeExpense(currentDate)),
      tap((data:any) => {

        this.childLevelCashBankBalance = data.childLevelCashAndBankBalance,
        this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
        this.cashBankBalanceDetails = data.details
      })
    ).subscribe()



    let requierdData$ = combineLatest([
      this.routeService.geography$,
      this.routeService.date$
    ]).pipe(
      map(([currentGeography, CurrentDate]) => {
        if(currentGeography.level == 'division') {
        return this.service.getDivisionLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.childLevelCashBankBalance = data.childLevelCashAndBankBalance,
              this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
              this.cashBankBalanceDetails = data.details
             })

          )
          .subscribe()
       }
       return of('')
       })
    )
    requierdData$.subscribe()
  }
}
