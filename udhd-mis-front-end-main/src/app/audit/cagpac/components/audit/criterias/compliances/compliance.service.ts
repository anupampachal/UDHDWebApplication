import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { CAGAuditServerConstant } from "src/app/audit/cagpac/cag-audit-server-constants";
import { AuditComplianceDTO } from "src/app/audit/cagpac/models/cag-compliances.model";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class CAGAuditComplianceService {
    constructor(private http: HttpClient) { }

    findCAGAuditComplianceById(id: number): Observable<AuditComplianceDTO> {
        return this.http.get<AuditComplianceDTO>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_COMPLIANCE_BY_ID + "/" + id);
    }

    getCAGAuditComplianceByPage(data: PaginationModel): Observable<GenericResponseObject<AuditComplianceDTO>> {
        return this.http.post<GenericResponseObject<AuditComplianceDTO>>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_COMPLIANCE_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createCAGAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.post<AuditComplianceDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMPLIANCE, requestData);
    }

    updateCAGAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.put<AuditComplianceDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMPLIANCE, requestData);
    }

}