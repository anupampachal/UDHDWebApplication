import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { AGIRAuditServerConstant } from "src/app/audit/ag-ir/agir-audit-server-constants";
import { AuditComplianceDTO } from "src/app/audit/ag-ir/models/agircompliances.model";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class AGIRAuditComplianceService {
    constructor(private http: HttpClient) { }

    findAGIRAuditComplianceById(id: number): Observable<AuditComplianceDTO> {
        return this.http.get<AuditComplianceDTO>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_COMPLIANCE_BY_ID + "/" + id);
    }

    getAGIRAuditComplianceByPage(data: PaginationModel): Observable<GenericResponseObject<AuditComplianceDTO>> {
        return this.http.post<GenericResponseObject<AuditComplianceDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_COMPLIANCE_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createAGIRAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.post<AuditComplianceDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMPLIANCE, requestData);
    }

    updateAGIRAuditCompliance(requestData: AuditComplianceDTO): Observable<AuditComplianceDTO> {
        return this.http.put<AuditComplianceDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMPLIANCE, requestData);
    }

}