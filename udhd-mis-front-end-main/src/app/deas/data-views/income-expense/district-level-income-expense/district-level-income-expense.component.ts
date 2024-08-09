import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, Observable, of } from 'rxjs';
import {  map, tap } from 'rxjs/operators';
import { IncomeExpenseService } from 'src/app/deas/services/income-expense.service';
import { RouteService } from 'src/app/deas/services/route.service';
import { DateRangeDTO } from 'src/app/deas/sharedFilters/date-range-dto';

@Component({
  selector: 'app-district-level-income-expense',
  templateUrl: './district-level-income-expense.component.html',
  styleUrls: ['./district-level-income-expense.component.css']
})
export class DistrictLevelIncomeExpenseComponent implements OnInit {

  routeArray: ActivatedRoute[] = [];
  divisionLevelData$!: Observable<any>
  currentDateRange!: DateRangeDTO;
  currentDivisionId!: number;
  childLevelIncomeAndExpenseData = []
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
        if(currentGeography.level == 'district') {
        return this.incomeExpenseService.getDistrictLevelIncomeExpense(currentGeography.id, CurrentDate)
          .pipe(
            tap((data: any) => {
              this.childLevelIncomeAndExpenseData = data.childLevelIncomeAndExpenseData,
                this.headLevelExpenseInfo = data.headLevelExpenseInfo
              this.headLevelIncomeInfo = data.headLevelIncomeInfo
              this.mainIncomeAndExpenseComparativeData = data.mainIncomeAndExpenseComparativeData
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
