import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { FinanceAuditServerConstant } from "src/app/audit/finance/finance-audit-server-constants";
import { AuditComplianceDTO } from "src/app/audit/finance/models/financecompliances.model";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class FinanceAuditComplianceService {
    constructor(private http: HttpClient) { }

    findFinanceAuditComplianceById(id: string): Observable<AuditComplianceDTO> {
        return this.http.get<AuditComplianceDTO>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_COMPLIANCE_BY_ID + "/" + id);
    }

    getFinanceAuditComplianceByPage(data: PaginationModel): Observable<GenericResponseObject<AuditComplianceDTO>> {
        return this.http.post<GenericResponseObject<AuditComplianceDTO>>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_COMPLIANCE_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createFinanceAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.post<AuditComplianceDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMPLIANCE, requestData);
    }

    updateFinanceAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.put<AuditComplianceDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMPLIANCE, requestData);
    }

}