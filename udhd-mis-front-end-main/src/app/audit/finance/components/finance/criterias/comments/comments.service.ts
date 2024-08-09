import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { FinanceAuditServerConstant } from "src/app/audit/finance/finance-audit-server-constants";
import { AuditCommentDTO } from "src/app/audit/finance/models/financecomment.model";
import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "src/app/shared/model/pagination-request.model";

@Injectable()
export class FinanceAuditCommentService {
    constructor(private http: HttpClient) { }

    findFinanceAuditCommentById(id: string): Observable<AuditCommentDTO> {
        return this.http.get<AuditCommentDTO>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_COMMENT_BY_ID + "/" + id);
    }

    getFinanceAuditCommentByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCommentDTO>> {
        return this.http.post<GenericResponseObject<AuditCommentDTO>>(FinanceAuditServerConstant.Finance_AUDIT_GET_Finance_AUDIT_COMMMENTS_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createFinanceAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.post<AuditCommentDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMMENT, requestData);
    }

    updateFinanceAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.put<AuditCommentDTO>(FinanceAuditServerConstant.Finance_AUDIT_CREATE_UPDATE_Finance_AUDIT_COMMENT, requestData);
    }

}