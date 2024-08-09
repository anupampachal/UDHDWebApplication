import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "../../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../../shared/model/pagination-request.model";
import { CAGAuditServerConstant } from "../../../cag-audit-server-constants";
import { AuditCriteriaDTO } from "../../../models/cag-criteria.model";
import { BooleanResponseDTO } from "src/app/shared/model/boolean-response.model";

@Injectable()
export class CAGAuditCriteriaService {
    constructor(private http: HttpClient) { }

    findCAGAuditCriteriaById(id: number): Observable<AuditCriteriaDTO> {
        return this.http.get<AuditCriteriaDTO>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_CRITERIA_BY_ID + "/" + id);
    }

    getCAGAuditCriteriaByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCriteriaDTO>> {
        return this.http.post<GenericResponseObject<AuditCriteriaDTO>>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_CRITERIA_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createCAGAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.post<AuditCriteriaDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_CRITERIA, requestData);
    }

    updateCAGAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.put<AuditCriteriaDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_CRITERIA, requestData);
    }

    deleteCAGAuditCriteria(id: number): Observable<BooleanResponseDTO> {
        return this.http.delete<BooleanResponseDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT_CRITERIA+'/'+id);
    }

}