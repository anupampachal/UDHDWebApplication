import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/internal/operators/shareReplay';
import { InternalAuditDTO } from 'src/app/reports/models/internal-audit-dto';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { InternalAuditServerConstant } from './internal-audit-server-route-const';
import { AuditReportViewResponseDTO } from './models/audit-report-view-response.model';

@Injectable()
export class InternalAuditService{

  constructor(private http: HttpClient) { }

  getAuditReportByPage(data: PaginationModel): Observable<GenericResponseObject<AuditReportViewResponseDTO>> {
    return this.http.post<GenericResponseObject<AuditReportViewResponseDTO>>(InternalAuditServerConstant.INTERNAL_AUDIT_GET_INTERNAL_AUDIT_LIST_BY_PAGE, data).pipe(shareReplay(2));
  }
  findInternalAuditById(id: string): Observable<InternalAuditDTO> {
    // return this.http.get<CAGPACAuditDTO>(CAGPACAuditServerConstant.CAGPAC_AUDIT_GET_CAGPAC_AUDIT_BY_ID + "/" + id);
    return this.http.get<InternalAuditDTO>(InternalAuditServerConstant.INTERNAL_AUDIT_GET_INTERNAL_AUDIT_BY_ID + "/" + id);
}

}
