import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "../../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../../shared/model/pagination-request.model";
import { AGIRAuditServerConstant } from "../../../agir-audit-server-constants";
import { AuditCriteriaDTO } from "../../../models/agir-criteria.model";
import { BooleanResponseDTO } from "src/app/shared/model/boolean-response.model";

@Injectable()
export class AGIRAuditCriteriaService {
    constructor(private http: HttpClient) { }

    findAGIRAuditCriteriaById(id: number): Observable<AuditCriteriaDTO> {
        return this.http.get<AuditCriteriaDTO>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_CRITERIA_BY_ID + "/" + id);
    }

    getAGIRAuditCriteriaByPage(data: PaginationModel): Observable<GenericResponseObject<AuditCriteriaDTO>> {
        return this.http.post<GenericResponseObject<AuditCriteriaDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_CRITERIA_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    createAGIRAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.post<AuditCriteriaDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_CRITERIA, requestData);
    }

    updateAGIRAuditCriteria(requestData: AuditCriteriaDTO): Observable<AuditCriteriaDTO> {
        return this.http.put<AuditCriteriaDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_CRITERIA, requestData);
    }

    deleteAGIRAuditCriteria(id: number): Observable<BooleanResponseDTO> {
        return this.http.delete<BooleanResponseDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT_CRITERIA+'/'+id);
    }

}