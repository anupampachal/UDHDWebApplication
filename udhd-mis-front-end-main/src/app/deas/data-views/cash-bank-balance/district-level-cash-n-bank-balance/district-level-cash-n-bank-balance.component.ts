import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, combineLatest, of } from 'rxjs';
import { switchMap, tap, map } from 'rxjs/operators';
import { CashBankBalanceService } from 'src/app/deas/services/cash-bank-balance.service';
import { RouteService } from 'src/app/deas/services/route.service';
import { DateRangeDTO } from 'src/app/deas/sharedFilters/date-range-dto';

@Component({
  selector: 'app-district-level-cash-n-bank-balance',
  templateUrl: './district-level-cash-n-bank-balance.component.html',
  styleUrls: ['./district-level-cash-n-bank-balance.component.css']
})
export class DistrictLevelCashNBankBalanceComponent implements OnInit {

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

    // this.routeService.date$.pipe(
    //   switchMap(currentDate =>
    //     this.service.getStateLevelIncomeExpense(currentDate)),
    //   tap((data:any) => {

    //     this.childLevelCashBankBalance = data.childLevelCashAndBankBalance,
    //     this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
    //     this.cashBankBalanceDetails = data.details
    //   })
    // ).subscribe()



    let requierdData$ = combineLatest([
      this.routeService.geography$,
      this.routeService.date$
    ]).pipe(
      map(([currentGeography, CurrentDate]) => {
        if(currentGeography.level == 'district') {
        return this.service.getDistrictLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.childLevelCashBankBalance = data.childLevelCashAndBankBalance,
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
