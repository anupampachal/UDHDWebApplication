import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
//import { CAGAuditServerConstant } from "src/app/audit/cagpac/cag-audit-server-constants";
//import { AuditCommentDTO } from "src/app/audit/cagpac/models/cagcomment.model";
//import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
//import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { CAGAuditServerConstant } from "../../../../../../audit/cagpac/cag-audit-server-constants";
import { AuditCommentDTO } from "../../../../../../audit/cagpac/models/cag-comment.model";
import { GenericResponseObject } from "../../../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../../../shared/model/pagination-request.model";

@Injectable()
export class CAGAuditCommentService {
    constructor(private http: HttpClient) { }

    findCAGAuditCommentById(id: number): Observable<AuditCommentDTO> {
        return this.http.get<AuditCommentDTO>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_COMMENT_BY_ID + "/" + id);
    }

    getCAGAuditCommentByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCommentDTO>> {
        return this.http.post<GenericResponseObject<AuditCommentDTO>>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_COMMMENTS_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createCAGAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.post<AuditCommentDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMMENT, requestData);
    }

    updateCAGAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.put<AuditCommentDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_COMMENT, requestData);
    }

}