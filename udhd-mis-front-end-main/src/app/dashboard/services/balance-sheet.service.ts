import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { DateRangeDTO } from '../sharedFilters/date-range-dto';

@Injectable()
export class BalanceSheetService {
  public static BASE_URL = environment.serverUrl;
  public static BALANCE_SHEET_BASE_URL = BalanceSheetService.BASE_URL + '/deas/balance-sheet';
  public static BALANCE_SHEET_GET_STATE_LEVEL_DATA = BalanceSheetService.BALANCE_SHEET_BASE_URL + '/state';
  public static BALANCE_SHEET_GET_DIVISION_LEVEL_DATA = BalanceSheetService.BALANCE_SHEET_BASE_URL + '/division';
  public static BALANCE_SHEET_GET_DISTRICT_LEVEL_DATA = BalanceSheetService.BALANCE_SHEET_BASE_URL + '/district';
  public static BALANCE_SHEET_GET_ULB_LEVEL_DATA = BalanceSheetService.BALANCE_SHEET_BASE_URL + '/ulb';

  constructor(private http: HttpClient) { }

  getStateLevelIncomeExpense(dateRange: DateRangeDTO) {
   return this.http.post(BalanceSheetService.BALANCE_SHEET_GET_STATE_LEVEL_DATA, {}, {
    params: new HttpParams()
    .set('dateTill', dateRange.endDate)
   })
  }

  getDivisionLevelIncomeExpense(divisionId: number,dateRange: DateRangeDTO) {

    return this.http.post(BalanceSheetService.BALANCE_SHEET_GET_DIVISION_LEVEL_DATA + `/${divisionId}`, {}, {
      params: new HttpParams()
      .set('dateTill', dateRange.endDate)
    })
  }

  getDistrictLevelIncomeExpense(districtId: number,dateRange: DateRangeDTO) {

    return this.http.post(BalanceSheetService.BALANCE_SHEET_GET_DISTRICT_LEVEL_DATA + '/' + districtId, {}, {
       params: new HttpParams()
       .set('dateTill', dateRange.endDate)
     })
   }

   getUlbLevelIncomeExpense(ulbId: number,dateRange: DateRangeDTO) {
    return this.http.post(BalanceSheetService.BALANCE_SHEET_GET_ULB_LEVEL_DATA + `/${ulbId}`, {}, {
       params: new HttpParams()
       .set('dateTill', dateRange.endDate)
     })
   }

}
