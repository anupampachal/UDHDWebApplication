import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from '../shared/model/generic-response-object-pagination.model';
import { PaginationModel } from '../shared/model/pagination-request.model';
import { AgirDTO } from './models/agir-dto';
import { CagpacDTO } from './models/cagpac-dto';
import { FinanceAuditDTO } from './models/finance-audit-dto';
import { InternalAuditDTO } from './models/internal-audit-dto';
import { UcDTO } from './models/uc-dto';
import { ReportsServerRouteConstants } from './reports-server-route-constants';

@Injectable()
export class ReportsService {
  constructor(private http: HttpClient) { }

  findAgirById(id: string): Observable<AgirDTO> {
    return this.http.get<AgirDTO>(ReportsServerRouteConstants.STNG_FIND_AGIR_BY_ID_URL + "/" + id);
  }

  getAgirDataByPage(data: PaginationModel): Observable<GenericResponseObject<AgirDTO>> {
    return this.http.post<GenericResponseObject<AgirDTO>>(ReportsServerRouteConstants.STNG_GET_AGIR_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  findCagpacById(id: string): Observable<CagpacDTO> {
    return this.http.get<CagpacDTO>(ReportsServerRouteConstants.STNG_FIND_CAGPAC_BY_ID_URL + "/" + id);
  }

  getCagpacDataByPage(data: PaginationModel): Observable<GenericResponseObject<CagpacDTO>> {
    return this.http.post<GenericResponseObject<CagpacDTO>>(ReportsServerRouteConstants.STNG_GET_CAGPAC_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  findFinanceAuditById(id: string): Observable<FinanceAuditDTO> {
    return this.http.get<FinanceAuditDTO>(ReportsServerRouteConstants.STNG_FIND_FINANCE_AUDIT_BY_ID_URL + "/" + id);
  }

  getFinanceAuditDataByPage(data: PaginationModel): Observable<GenericResponseObject<FinanceAuditDTO>> {
    return this.http.post<GenericResponseObject<FinanceAuditDTO>>(ReportsServerRouteConstants.STNG_GET_FINANCE_AUDIT_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  findInternalAuditById(id: string): Observable<InternalAuditDTO> {
    return this.http.get<InternalAuditDTO>(ReportsServerRouteConstants.STNG_FIND_INTERNAL_AUDIT_BY_ID_URL + "/" + id);
  }

  getInternalAuditDataByPage(data: PaginationModel): Observable<GenericResponseObject<InternalAuditDTO>> {
    return this.http.post<GenericResponseObject<InternalAuditDTO>>(ReportsServerRouteConstants.STNG_GET_INTERNAL_AUDIT_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  findUcById(id: string): Observable<UcDTO> {
    return this.http.get<UcDTO>(ReportsServerRouteConstants.STNG_FIND_UC_BY_ID_URL + "/" + id);
  }

  getUcDataByPage(data: PaginationModel): Observable<GenericResponseObject<UcDTO>> {
    return this.http.post<GenericResponseObject<UcDTO>>(ReportsServerRouteConstants.STNG_GET_UC_BY_PAGE_URL, data).pipe(shareReplay(2));
  }
}
