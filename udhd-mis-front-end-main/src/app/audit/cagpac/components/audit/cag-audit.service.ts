import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { shareReplay } from "rxjs/operators";
import { GenericResponseObject } from "../../../../shared/model/generic-response-object-pagination.model";
import { PaginationModel } from "../../../../shared/model/pagination-request.model";
import { ULBDTO } from "../../../../shared/model/ulb.model";

import { CAGAuditServerConstant } from "../../cag-audit-server-constants"
import { CAGAuditDTO } from "../../models/cag-audit.model";
import { BooleanResponseDTO } from "src/app/shared/model/boolean-response.model";

@Injectable()
export class CAGAuditService {
    
    constructor(private http: HttpClient) { }

    findCAGAuditById(id: string): Observable<CAGAuditDTO> {
        return this.http.get<CAGAuditDTO>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_BY_ID + "/" + id);
    }

    getCAGAuditByPage(data: PaginationModel): Observable<GenericResponseObject<CAGAuditDTO>> {
        return this.http.post<GenericResponseObject<CAGAuditDTO>>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_LIST_BY_PAGE, data).pipe(shareReplay(2));
    }

    getULBForCreation(): Observable<ULBDTO> {
        return this.http.get<ULBDTO>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_AUDIT_ULB_LIST);
    }

    createCAGAudit(requestData: CAGAuditDTO): Observable<CAGAuditDTO> {
        return this.http.post<CAGAuditDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT, requestData);
    }

    deleteCAGAudit(id:number): Observable<BooleanResponseDTO> {
        return this.http.delete<BooleanResponseDTO>(CAGAuditServerConstant.AGIR_AUDIT_DELETE_CAG_AUDIT+ '/'+id);
    }

    updateCAGAudit(requestData: CAGAuditDTO): Observable<CAGAuditDTO> {
        return this.http.put<CAGAuditDTO>(CAGAuditServerConstant.CAG_AUDIT_CREATE_UPDATE_CAG_AUDIT, requestData);
    }

    downloadCAGAuditReport(id: number) {
        return this.http.get(CAGAuditServerConstant.CAG_AUDIT_REPORT_DOWNLOAD_PATH + '/' + id, { responseType: 'blob' });
    }

    sendCAGForAudit(id:number){
        return this.http.put<BooleanResponseDTO>(CAGAuditServerConstant.CAG_AUDIT_SEND_CAG_FOR_REVIEW+'/'+id,{});
    }

    getCAGAuditPendingUnassigned(data: PaginationModel):Observable<GenericResponseObject<CAGAuditDTO>>{
        return this.http.post<GenericResponseObject<CAGAuditDTO>>(CAGAuditServerConstant.CAG_AUDIT_GET_CAG_PENDING_FOR_REVIEW_UNASSIGNED,data);
    }

    assignCagAuditToMe(id: number) :Observable<CAGAuditDTO>{
        return this.http.put<CAGAuditDTO>(CAGAuditServerConstant.CAG_AUDIT_ASSIGN_TO_ME + '/' + id,{});
      }
    getCAGAuditsAssignedToMe(data:PaginationModel):Observable<GenericResponseObject<CAGAuditDTO>>{
        return this.http.post<GenericResponseObject<CAGAuditDTO>>(CAGAuditServerConstant.CAG_AUDIT_ASSIGNED_TO_ME,data);
    }  

    approveRejectCAGAudit(id:number,view:boolean):Observable<CAGAuditDTO>{
        return this.http.put<CAGAuditDTO>(CAGAuditServerConstant.CAG_AUDIT_APPROVE_REJECT+'/'+id+'/'+view,{});
    }

}