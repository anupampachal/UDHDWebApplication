import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { environment } from 'src/environments/environment';
import { ulbDTO } from '../models/ulbDTO';
import { MatSnackBar } from '@angular/material/snack-bar'
import { IAStep1ResponseDTO } from '../models/step1DTO';
import { BooleanResponseDTO } from 'src/app/shared/model/boolean-response.model';
@Injectable()
export class Step1ReportService {
  
  public static BASE_URL = environment.serverUrl;
  public static STEP1_BASE_URL = Step1ReportService.BASE_URL + '/audit/ia/step1';
  public static CREATE_STEP1_REPORT_URL = Step1ReportService.STEP1_BASE_URL + '/create-step1-report';
  public static GET_STEP1_PAGE_URL = Step1ReportService.STEP1_BASE_URL + '/info';
  public static GET_STEP1_UNASSIGNED_PAGE_URL = Step1ReportService.STEP1_BASE_URL + '/get-ia-audit-unassigned';
  public static GET_ULBS_FOR_USER = Step1ReportService.STEP1_BASE_URL + '/ulbs'
  public static GET_IA_REPORT = Step1ReportService.BASE_URL + "/audit/report/ia";
  public static SEND_FOR_REVIEW= Step1ReportService.STEP1_BASE_URL+'/send-ia-for-review-slpmu';
  public static ASSIGN_TO_ME= Step1ReportService.STEP1_BASE_URL+'/assign-to-me';
  public static GET_IA_ASSIGNED_TO_ME= Step1ReportService.STEP1_BASE_URL+'/get-ia-assigned-to-me';
  public static IA_AUDIT_APPROVE_REJECT=Step1ReportService.STEP1_BASE_URL+'/approve-reject';
  step1Report = {
    id: 1,
    ulbName: ""
  }

  constructor(private http: HttpClient, private snack: MatSnackBar) { }


  findStep1ReportById(id: number): Observable<IAStep1ResponseDTO> {
    return this.http.get<IAStep1ResponseDTO>(Step1ReportService.STEP1_BASE_URL + "/" + id);
  }

  getStep1ReportByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(Step1ReportService.GET_STEP1_PAGE_URL, data).pipe(shareReplay(2));
  }

  

  createStep1Report(requestData: any): Observable<any> {
    return this.http.put<any>(Step1ReportService.CREATE_STEP1_REPORT_URL, requestData);
  }

  deleteStep1Report(id: number) {
    return this.http.delete(Step1ReportService.STEP1_BASE_URL + "/" + id)
  }

  getULBsToCreateStep1Report(): Observable<ulbDTO[]> {
    return this.http.get<ulbDTO[]>(Step1ReportService.GET_ULBS_FOR_USER)
  }
  show(message: string, type: string, duration?: any) {
    this.snack.open(message, '', {
      duration: duration ? duration : 2000,
      panelClass: [type, 'snackbar'],
    });
  }

  downloadIAReport(id: number): Observable<Blob> {
    return this.http.get(Step1ReportService.GET_IA_REPORT + '/' + id, { responseType: 'blob' });
  }

  sendForReview(id:number):Observable<BooleanResponseDTO>{
    return this.http.put<BooleanResponseDTO>(Step1ReportService.SEND_FOR_REVIEW+'/'+id,{});
  }

  assignIAAuditToMe(id: number): Observable<IAStep1ResponseDTO> {
    return this.http.put<IAStep1ResponseDTO>(Step1ReportService.ASSIGN_TO_ME+'/'+id,{})
  }
  
  getStep1ReportUnasignedByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(Step1ReportService.GET_STEP1_UNASSIGNED_PAGE_URL, data).pipe(shareReplay(2));
  }
  getStep1ReportAssignedToMeByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(Step1ReportService.GET_IA_ASSIGNED_TO_ME, data).pipe(shareReplay(2));
  }

  approveRejectIAAudit(id:number,view:boolean):Observable<IAStep1ResponseDTO> {
    return this.http.put<IAStep1ResponseDTO>(Step1ReportService.IA_AUDIT_APPROVE_REJECT+'/'+id+'/'+view,{});
  }
  
  

}

