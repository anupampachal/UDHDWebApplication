import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { FinanceAuditServerConstant } from "../../../finance-audit-server-constants";
import { AuditCriteriaDTO } from "../../../models/finance-criteria.model";

@Injectable()
export class FinanceAuditCriteriaService {
    constructor(private http: HttpClient) { }

    findFinanceAuditCriteriaById(id: number): Observable<AuditCriteriaDTO> {
        return this.http.get<AuditCriteriaDTO>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_CRITERIA_BY_ID + "/" + id);
    }

    getFinanceAuditCriteriaByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCriteriaDTO>> {
        return this.http.post<GenericResponseObject<AuditCriteriaDTO>>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_CRITERIA_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createFinanceAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.post<AuditCriteriaDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_CRITERIA, requestData);
    }

    updateFinanceAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.put<AuditCriteriaDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_CRITERIA, requestData);
    }

}