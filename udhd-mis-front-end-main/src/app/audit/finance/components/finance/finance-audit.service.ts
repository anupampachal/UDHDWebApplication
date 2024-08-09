import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { ULBDTO } from "src/app/shared/model/ulb.model";
import { FinanceAuditServerConstant } from "../../finance-audit-server-constants";
import { FinanceAuditDTO } from "../../models/finance-audit.model";

@Injectable()
export class FinanceAuditService {
    constructor(private http: HttpClient) { }

    findFinanceAuditById(id: string): Observable<FinanceAuditDTO> {
        return this.http.get<FinanceAuditDTO>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_BY_ID + "/" + id);
    }

    getFinanceAuditByPage(data: PaginationModel): Observable<GenericResponseObject<FinanceAuditDTO>> {
        return this.http.post<GenericResponseObject<FinanceAuditDTO>>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    getULBForCreation(): Observable<ULBDTO> {
        return this.http.get<ULBDTO>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_ULB_LIST);
    }

    createFinanceAudit(requestData: FinanceAuditDTO): Observable<FinanceAuditDTO> {
        return this.http.post<FinanceAuditDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT, requestData);
    }

    updateFinanceAudit(requestData: FinanceAuditDTO): Observable<FinanceAuditDTO> {
        return this.http.put<FinanceAuditDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT, requestData);
    }

}