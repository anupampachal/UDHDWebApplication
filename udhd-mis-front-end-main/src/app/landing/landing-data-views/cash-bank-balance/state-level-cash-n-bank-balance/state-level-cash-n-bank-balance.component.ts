import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { CashBankBalanceService } from 'src/app/landing/services/cash-bank-balance.service';
import { RouteService } from 'src/app/landing/services/route.service';
import { DateRangeDTO } from 'src/app/landing/sharedFilters/date-range-dto';

@Component({
  selector: 'app-state-level-cash-n-bank-balance',
  templateUrl: './state-level-cash-n-bank-balance.component.html',
  styleUrls: ['./state-level-cash-n-bank-balance.component.css']
})

export class StateLevelCashNBankBalanceComponent implements OnInit {

  stateLevelData$!: Observable<any>
  childLevelCashBankBalance = []
  cashBankBalanceDetails = []
  mainCashBankBalanceComparativeData = []
  constructor(
    private routeService: RouteService,
    private service: CashBankBalanceService
  ) {
  }

  ngOnInit(): void {

    this.routeService.date$.pipe(
      switchMap(currentDate =>
        this.service.getStateLevelIncomeExpense(currentDate)),
      tap((data:any) => {

        this.childLevelCashBankBalance = data.childLevelCashAndBankBalance,
        this.mainCashBankBalanceComparativeData = data.curretLevelCashAndBankBalance
        this.cashBankBalanceDetails = data.details
      })
    ).subscribe()
  }
}

