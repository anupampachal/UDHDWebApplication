import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
//import { AGIRAuditServerConstant } from "src/app/audit/ag-ir/agir-audit-server-constants";
//import { AuditCommentDTO } from "src/app/audit/ag-ir/models/agircomment.model";
//import { GenericResponseObject } from "src/app/shared/model/generic-response-object-pagination.model";
//import { PaginationModel } from "src/app/shared/model/pagination-request.model";
import { AGIRAuditServerConstant } from "../../../../../../audit/ag-ir/agir-audit-server-constants";
import { AuditCommentDTO } from "../../../../../../audit/ag-ir/models/agircomment.model";
import { GenericResponseObject } from "../../../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../../../shared/model/pagination-request.model";

@Injectable()
export class AGIRAuditCommentService {
    constructor(private http: HttpClient) { }

    findAGIRAuditCommentById(id: number): Observable<AuditCommentDTO> {
        return this.http.get<AuditCommentDTO>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_COMMENT_BY_ID + "/" + id);
    }

    getAGIRAuditCommentByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCommentDTO>> {
        return this.http.post<GenericResponseObject<AuditCommentDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_COMMMENTS_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createAGIRAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.post<AuditCommentDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMMENT, requestData);
    }

    updateAGIRAuditComment(requestData: AuditCommentDTO): Observable<AuditCommentDTO> {
        return this.http.put<AuditCommentDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_COMMENT, requestData);
    }

}