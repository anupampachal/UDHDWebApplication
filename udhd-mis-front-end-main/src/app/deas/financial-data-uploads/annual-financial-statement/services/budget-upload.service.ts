import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay, tap } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { FinYearDateDTO } from '../fin-year-date-dto';
import { BudgetUploadDTO } from '../models/budget-upload-dto';

@Injectable()
export class BudgetUploadService {

  constructor(
    private http: HttpClient
  ) { }

  public static BASE_URL = environment.serverUrl;
  public static BUDGET_UPLOAD_BASE_URL = BudgetUploadService.BASE_URL + '/deas/budget-upload';
  public static GET_ULB_DATA_URL = BudgetUploadService.BUDGET_UPLOAD_BASE_URL + '/ulb';
  public static UPLOAD_BUDGET_DATA_URL = BudgetUploadService.BUDGET_UPLOAD_BASE_URL + '/file/upload';
  public static GET_BUDGET_DATA_FILE_URL = BudgetUploadService.BUDGET_UPLOAD_BASE_URL + "/file";
  public static GET_FINANCIAL_YEAR_DATA_URL = BudgetUploadService.BUDGET_UPLOAD_BASE_URL + '/fin-years';
  public static GET_BUDGET_UPLOAD_BY_PAGE_URL = BudgetUploadService.BASE_URL + '/deas/budget-upload/info';

  findBudgetById(id: string): Observable<BudgetUploadDTO> {
    return this.http.get<BudgetUploadDTO>(BudgetUploadService.BUDGET_UPLOAD_BASE_URL + "/" + id);
  }

  getBudgetByPage(data: PaginationModel): Observable<GenericResponseObject<BudgetUploadDTO>> {
    return this.http.post<GenericResponseObject<BudgetUploadDTO>>(BudgetUploadService.GET_BUDGET_UPLOAD_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  getULBForBudget(): Observable<ULBDTO> {
    return this.http.get<ULBDTO>(BudgetUploadService.GET_ULB_DATA_URL)
  }
  uploadBudgetData(file: File, budgetData: BudgetUploadDTO) {

    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('startDate', budgetData.inputData.startDate);
    formdata.append('endDate', budgetData.inputData.endDate);
    formdata.append('inputDate', budgetData.inputData.inputDate);
    // formdata.append('quarter', budgetData.quarter);
    formdata.append('ulbId', budgetData.ulbId)
    return this.http.post<FinYearDateDTO>(BudgetUploadService.UPLOAD_BUDGET_DATA_URL,
      formdata, {
      reportProgress: true,
    }
    )
  }
  getFileByFileId(fileId: string) {
    return this.http.get(BudgetUploadService.GET_BUDGET_DATA_FILE_URL + '/' + fileId, { responseType: 'blob' });
  }

  getFinancialYearData() {
    return this.http.post(BudgetUploadService.GET_FINANCIAL_YEAR_DATA_URL, { "criteria": "ALL", "pageNo": 0, "pageSize": 10 })
  }

}
