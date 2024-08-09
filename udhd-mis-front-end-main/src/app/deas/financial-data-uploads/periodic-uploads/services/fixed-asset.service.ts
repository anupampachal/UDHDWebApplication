import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { FinYearDateDTO } from '../fin-year-date-dto';
import { FixedAssetDTO } from '../models/fixed-asset-dto';

@Injectable({
  providedIn: 'root'
})
export class FixedAssetService {

  constructor(
    private http: HttpClient
  ) { }

  public static BASE_URL = environment.serverUrl;
  public static FIXED_ASSET_BASE_URL = FixedAssetService.BASE_URL + '/deas/fixed-assets';
  public static GET_ULB_DATA_URL = FixedAssetService.FIXED_ASSET_BASE_URL + '/ulb';
  public static UPLOAD_BUDGET_DATA_URL = FixedAssetService.FIXED_ASSET_BASE_URL + '/file/upload';
  public static GET_BUDGET_DATA_FILE_URL = FixedAssetService.FIXED_ASSET_BASE_URL+ "/file";
  public static GET_FINANCIAL_YEAR_DATA_URL = FixedAssetService.FIXED_ASSET_BASE_URL + '/fin-years';
  public static GET_FIXED_ASSET_UPLOAD_BY_PAGE_URL = FixedAssetService.FIXED_ASSET_BASE_URL + '/info';

  findFixedAssetById(id: string): Observable<FinYearDateDTO> {
    return this.http.get<FinYearDateDTO>(FixedAssetService.FIXED_ASSET_BASE_URL + "/" + id);
  }

  getFixedAssetByPage(data: PaginationModel): Observable<GenericResponseObject<FixedAssetDTO>> {
    return this.http.post<GenericResponseObject<FixedAssetDTO>>(FixedAssetService.GET_FIXED_ASSET_UPLOAD_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  getULBForFixedAsset(): Observable<ULBDTO> {
    return this.http.get<ULBDTO>(FixedAssetService.GET_ULB_DATA_URL)
  }
  uploadFixedAssetData(file: File, budgetData: FixedAssetDTO) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('startDate', budgetData.inputData.startDate);
    formdata.append('endDate', budgetData.inputData.endDate);
    formdata.append('inputDate', budgetData.inputData.inputDate);
    formdata.append('quarter', budgetData.quarter);
    formdata.append('ulbId', budgetData.ulbId)
    return this.http.post<FinYearDateDTO>(FixedAssetService.UPLOAD_BUDGET_DATA_URL,
      formdata, {
      reportProgress: true,
    }
    );
  }
  getFileByFileId(fileId: string) {
    return this.http.get(FixedAssetService.GET_BUDGET_DATA_FILE_URL + '/' + fileId, { responseType: 'blob' });
  }

  getFinancialYearData() {
    return this.http.post(FixedAssetService.GET_FINANCIAL_YEAR_DATA_URL, { "criteria": "ALL", "pageNo": 0, "pageSize": 10 })
  }

}
