import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../shared/model/pagination-request.model";
import { ULBDTO } from "../../../../shared/model/ulb.model";

import { AGIRAuditServerConstant } from "../../agir-audit-server-constants";
import { AGIRAuditDTO } from "../../models/agir-audit.model";
import { BooleanResponseDTO } from "src/app/shared/model/boolean-response.model";

@Injectable()
export class AGIRAuditService {
    
    constructor(private http: HttpClient) { }

    findAGIRAuditById(id: string): Observable<AGIRAuditDTO> {
        return this.http.get<AGIRAuditDTO>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_BY_ID + "/" + id);
    }

    getAGIRAuditByPage(data: PaginationModel): Observable<GenericResponseObject<AGIRAuditDTO>> {
        return this.http.post<GenericResponseObject<AGIRAuditDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    getULBForCreation(): Observable<ULBDTO> {
        return this.http.get<ULBDTO>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_AUDIT_ULB_LIST);
    }

    createAGIRAudit(requestData: AGIRAuditDTO): Observable<AGIRAuditDTO> {
        return this.http.post<AGIRAuditDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT, requestData);
    }

    deleteAGIRAudit(id:number): Observable<BooleanResponseDTO> {
        return this.http.delete<BooleanResponseDTO>(AGIRAuditServerConstant.AGIR_AUDIT_DELETE_AGIR_AUDIT+ '/'+id);
    }

    updateAGIRAudit(requestData: AGIRAuditDTO): Observable<AGIRAuditDTO> {
        return this.http.put<AGIRAuditDTO>(AGIRAuditServerConstant.AGIR_AUDIT_CREATE_UPDATE_AGIR_AUDIT, requestData);
    }

    downloadAGIRAuditReport(id: number) {
        return this.http.get(AGIRAuditServerConstant.AGIR_AUDIT_REPORT_DOWNLOAD_PATH + '/' + id, { responseType: 'blob' });
    }

    sendAGIRForAudit(id:number){
        return this.http.put<BooleanResponseDTO>(AGIRAuditServerConstant.AGIR_AUDIT_SEND_AGIR_FOR_REVIEW+'/'+id,{});
    }

    getAGIRAuditPendingUnassigned(data: PaginationModel):Observable<GenericResponseObject<AGIRAuditDTO>>{
        return this.http.post<GenericResponseObject<AGIRAuditDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_GET_AGIR_PENDING_FOR_REVIEW_UNASSIGNED,data);
    }

    assignAgirAuditToMe(id: number) :Observable<AGIRAuditDTO>{
        return this.http.put<AGIRAuditDTO>(AGIRAuditServerConstant.AGIR_AUDIT_ASSIGN_TO_ME + '/' + id,{});
      }
    getAGIRAuditsAssignedToMe(data:PaginationModel):Observable<GenericResponseObject<AGIRAuditDTO>>{
        return this.http.post<GenericResponseObject<AGIRAuditDTO>>(AGIRAuditServerConstant.AGIR_AUDIT_ASSIGNED_TO_ME,data);
    }  

    approveRejectAGIRAudit(id:number,view:boolean):Observable<AGIRAuditDTO>{
        return this.http.put<AGIRAuditDTO>(AGIRAuditServerConstant.AGIR_AUDIT_APPROVE_REJECT+'/'+id+'/'+view,{});
    }

}