import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { FinYearDateDTO } from '../fin-year-date-dto';
import { TrialBalanceDTO } from '../models/trial-balance-dto';

@Injectable()
export class TrialBalanceService {

  constructor(
    private http: HttpClient
  ) { }

  public static BASE_URL = environment.serverUrl;
  public static TRIAL_BALANCE_BASE_URL = TrialBalanceService.BASE_URL + '/deas/trial-balance';
  public static GET_ULB_DATA_URL = TrialBalanceService.TRIAL_BALANCE_BASE_URL + '/ulb';
  public static UPLOAD_TRIAL_BALANCE_DATA_URL = TrialBalanceService.TRIAL_BALANCE_BASE_URL + '/file/upload';
  public static GET_TRIAL_BALANCE_FILE_URL = TrialBalanceService.TRIAL_BALANCE_BASE_URL + "/file/tb";
  public static GET_FINANCIAL_YEAR_DATA_URL = TrialBalanceService.TRIAL_BALANCE_BASE_URL + '/fin-years';
  public static GET_TRIAL_BALANCE_UPLOAD_BY_PAGE_URL = TrialBalanceService.TRIAL_BALANCE_BASE_URL + '/info';
  public static GET_TRIAL_BALANCE_SUMMARY_FILE = TrialBalanceService.TRIAL_BALANCE_BASE_URL + '/file/summary';

  findTrialBalanceById(id: string): Observable<TrialBalanceDTO> {
    return this.http.get<TrialBalanceDTO>(TrialBalanceService.TRIAL_BALANCE_BASE_URL + "/" + id);
  }

  getTrialBalanceByPage(data: PaginationModel): Observable<GenericResponseObject<TrialBalanceDTO>> {
    return this.http.post<GenericResponseObject<TrialBalanceDTO>>(TrialBalanceService.GET_TRIAL_BALANCE_UPLOAD_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  getULBForTrialBalance(): Observable<ULBDTO> {
    return this.http.get<ULBDTO>(TrialBalanceService.GET_ULB_DATA_URL)
  }
  uploadTrialBalanceData(file: File) {

    const formdata: FormData = new FormData()
    formdata.append('file', file);
    return this.http.post(TrialBalanceService.UPLOAD_TRIAL_BALANCE_DATA_URL,
      formdata
    )
  }
  getFileByFileId(fileId: string) {
    return this.http.get(TrialBalanceService.GET_TRIAL_BALANCE_FILE_URL + '/' + fileId, { responseType: 'blob' });
  }

  getFinancialYearData() {
    return this.http.post(TrialBalanceService.GET_FINANCIAL_YEAR_DATA_URL, { "criteria": "ALL", "pageNo": 0, "pageSize": 10 })
  }

  getSummaryFile(fileId: string) {
    return this.http.get(TrialBalanceService.GET_TRIAL_BALANCE_SUMMARY_FILE + '/' + fileId, { responseType: 'blob' });

  }
}
