import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { IncomeExpenseService } from 'src/app/deas/services/income-expense.service';
import { RouteService } from 'src/app/deas/services/route.service';
import { DateRangeDTO } from 'src/app/deas/sharedFilters/date-range-dto';

@Component({
  selector: 'app-state-levl-income-expense',
  templateUrl: './state-level-income-expense.component.html',
  styleUrls: ['./state-level-income-expense.component.css']
})
export class StateLevelIncomeExpenseComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  divisionHierarchy!: any;
  stateLevelData$!: Observable<any>
  currentDateRange!: DateRangeDTO;

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
    this.routeService.date$.pipe(
      switchMap(currentDate =>
        this.incomeExpenseService.getStateLevelIncomeExpense(currentDate)),
      tap((data:any) => {
        this.childLevelIncomeAndExpenseData = data.childLevelIncomeAndExpenseData,
        this.headLevelExpenseInfo = data.headLevelExpenseInfo
        this.headLevelIncomeInfo = data.headLevelIncomeInfo
        this.mainIncomeAndExpenseComparativeData = data.mainIncomeAndExpenseComparativeData
      })
    ).subscribe()
  }
}
