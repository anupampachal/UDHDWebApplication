import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, combineLatest, of } from 'rxjs';
import { switchMap, tap, map } from 'rxjs/operators';
import { CashBankBalanceService } from 'src/app/dashboard/services/cash-bank-balance.service';
import { RouteService } from 'src/app/dashboard/services/route.service';
import { DateRangeDTO } from 'src/app/dashboard/sharedFilters/date-range-dto';

@Component({
  selector: 'app-ulb-level-cash-n-bank-balance',
  templateUrl: './ulb-level-cash-n-bank-balance.component.html',
  styleUrls: ['./ulb-level-cash-n-bank-balance.component.css']
})
export class UlbLevelCashNBankBalanceComponent implements OnInit {

  routeArray: ActivatedRoute[] = [];
  divisionHierarchy!: any;
  stateLevelData$!: Observable<any>
  currentDateRange!: DateRangeDTO;

  cashBankBalanceDetails = []
  mainCashBankBalanceComparativeData = []
  constructor(
    private route: ActivatedRoute,
    private routeService: RouteService,
    private service: CashBankBalanceService
  ) {
  }

  ngOnInit(): void {
    // this.routeService.date$.pipe(
    //   switchMap(currentDate =>
    //     this.service.getStateLevelIncomeExpense(currentDate)),
    //   tap((data:any) => {
    //
    //     this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
    //     this.cashBankBalanceDetails = data.details
    //   })
    // ).subscribe()

    let requierdData$ = combineLatest([
      this.routeService.geography$,
      this.routeService.date$
    ]).pipe(
      map(([currentGeography, CurrentDate]) => {
        if(currentGeography.level == 'ulb') {
        return this.service.getUlbLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
              this.cashBankBalanceDetails = data.details
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
