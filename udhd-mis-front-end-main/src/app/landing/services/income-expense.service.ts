import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { DateRangeDTO } from '../sharedFilters/date-range-dto';
import { RouteService } from './route.service';

@Injectable()
export class IncomeExpenseService {
  public static BASE_URL = environment.serverUrl;
  public static INCOME_EXPENSE_BASE_URL = IncomeExpenseService.BASE_URL + '/deas/income-expense';
  public static INCOME_EXPENSE_GET_STATE_LEVEL_DATA = IncomeExpenseService.INCOME_EXPENSE_BASE_URL + '/state';
  public static INCOME_EXPENSE_GET_DIVISION_LEVEL_DATA = IncomeExpenseService.INCOME_EXPENSE_BASE_URL + '/division';
  public static INCOME_EXPENSE_GET_DISTRICT_LEVEL_DATA = IncomeExpenseService.INCOME_EXPENSE_BASE_URL + '/district';
  public static INCOME_EXPENSE_GET_ULB_LEVEL_DATA = IncomeExpenseService.INCOME_EXPENSE_BASE_URL + '/ulb';

  constructor(private http: HttpClient, private routeService: RouteService) { }

  getStateLevelIncomeExpense(dateRange: DateRangeDTO) {
   return this.http.post(IncomeExpenseService.INCOME_EXPENSE_GET_STATE_LEVEL_DATA, {}, {
    params: new HttpParams()
    .set('startDate', dateRange.startDate)
    .set('endDate', dateRange.endDate)
   })
  }

  getDivisionLevelIncomeExpense(divisionId: number,dateRange: DateRangeDTO) {
    return this.http.post(IncomeExpenseService.INCOME_EXPENSE_GET_DIVISION_LEVEL_DATA + `/${divisionId}`, {}, {
      params: new HttpParams()
      .set('startDate', dateRange.startDate)
      .set('endDate', dateRange.endDate)
    })
  }

  getDistrictLevelIncomeExpense(districtId: number,dateRange: DateRangeDTO) {
    return this.http.post(IncomeExpenseService.INCOME_EXPENSE_GET_DISTRICT_LEVEL_DATA + '/' + districtId, {}, {
       params: new HttpParams()
       .set('startDate', dateRange.startDate)
       .set('endDate', dateRange.endDate)
     })
   }

   getUlbLevelIncomeExpense(ulbId: number,dateRange: DateRangeDTO) {
    return this.http.post(IncomeExpenseService.INCOME_EXPENSE_GET_ULB_LEVEL_DATA + `/${ulbId}`, {}, {
       params: new HttpParams()
       .set('startDate', dateRange.startDate)
       .set('endDate', dateRange.endDate)
     })
   }

}
