import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, Observable, of } from 'rxjs';
import {  map, tap } from 'rxjs/operators';
import { IncomeExpenseService } from 'src/app/deas/services/income-expense.service';
import { RouteService } from 'src/app/deas/services/route.service';
import { DateRangeDTO } from 'src/app/deas/sharedFilters/date-range-dto';

@Component({
  selector: 'app-ulb-level-income-expense',
  templateUrl: './ulb-level-income-expense.component.html',
  styleUrls: ['./ulb-level-income-expense.component.css']
})
export class UlbLevelIncomeExpenseComponent implements OnInit {

  routeArray: ActivatedRoute[] = [];
  divisionLevelData$!: Observable<any>
  currentDateRange!: DateRangeDTO;
  currentDivisionId!: number;
  headLevelExpenseInfo = []
  headLevelIncomeInfo = []
  mainIncomeAndExpenseComparativeData = []
  constructor(
    private route: ActivatedRoute,
    private routeService: RouteService,
    private incomeExpenseService: IncomeExpenseService
  ) {
  }

  ngOnInit(): void {

    this.routeArray = this.route.pathFromRoot;
    this.routeService.date$.subscribe(d => this.currentDateRange = d)
    this.routeService.geography$.subscribe(d => this.currentDivisionId = d.id)

    let requierdData$ = combineLatest([
      this.routeService.geography$,
      this.routeService.date$
    ]).pipe(
      map(([currentGeography, CurrentDate]) => {
        if(currentGeography.level == 'ulb') {
        return this.incomeExpenseService.getUlbLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.headLevelExpenseInfo = data.headLevelExpenseInfo
              this.headLevelIncomeInfo = data.headLevelIncomeInfo
              this.mainIncomeAndExpenseComparativeData = data.mainIncomeAndExpenseComparativeData
            })
          ).subscribe()
      }
    return of()
    })
    )
    requierdData$.subscribe()
}
}
