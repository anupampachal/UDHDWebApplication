import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { FinYearDateDTO } from '../fin-year-date-dto';
import { PropertyTaxRegisterDTO } from '../models/property-tax-register-dto';

@Injectable({
  providedIn: 'root'
})
export class PropertyTaxRegisterService {

    constructor(
      private http: HttpClient
    ) { }

    public static BASE_URL = environment.serverUrl;
    public static PROPERTY_TAX_REG_BASE_URL = PropertyTaxRegisterService.BASE_URL + '/deas/property-tax';
    public static GET_ULB_DATA_URL = PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL + '/ulb';
    public static UPLOAD_PROPERTY_TAX_DATA_URL = PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL + '/file/upload';
    public static GET_PROPERTY_TAX_DATA_FILE_URL = PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL+ "/file";
    public static GET_FINANCIAL_YEAR_DATA_URL = PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL + '/fin-years';
    public static GET_PROPERTY_TAX_UPLOAD_BY_PAGE_URL = PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL + '/info';

    findPropertyTaxRegById(id: string): Observable<PropertyTaxRegisterDTO> {
      return this.http.get<PropertyTaxRegisterDTO>(PropertyTaxRegisterService.PROPERTY_TAX_REG_BASE_URL + "/" + id);
    }

    getPropertyTaxRegByPage(data: PaginationModel): Observable<GenericResponseObject<PropertyTaxRegisterDTO>> {
      return this.http.post<GenericResponseObject<PropertyTaxRegisterDTO>>(PropertyTaxRegisterService.GET_PROPERTY_TAX_UPLOAD_BY_PAGE_URL, data).pipe(shareReplay(2));
    }

    getULBForPropertyTaxReg(): Observable<ULBDTO> {
      return this.http.get<ULBDTO>(PropertyTaxRegisterService.GET_ULB_DATA_URL)
    }
    uploadPropertyTaxRegData(file: File, budgetData: PropertyTaxRegisterDTO) {

      const formdata: FormData = new FormData()
      formdata.append('file', file);
      formdata.append('startDate', budgetData.inputData.startDate);
      formdata.append('endDate', budgetData.inputData.endDate);
      formdata.append('inputDate', budgetData.inputData.inputDate);
      formdata.append('quarter', budgetData.quarter);
      formdata.append('ulbId', budgetData.ulbId)
      return this.http.post<FinYearDateDTO>(PropertyTaxRegisterService.UPLOAD_PROPERTY_TAX_DATA_URL,
        formdata, {
        reportProgress: true,
      }
      )
    }
    getFileByFileId(fileId: string) {
      return this.http.get(PropertyTaxRegisterService.GET_PROPERTY_TAX_DATA_FILE_URL + '/' + fileId, { responseType: 'blob' });
    }

    getFinancialYearData() {
      return this.http.post(PropertyTaxRegisterService.GET_FINANCIAL_YEAR_DATA_URL, { "criteria": "ALL", "pageNo": 0, "pageSize": 10 })
    }

  }
