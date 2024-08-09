import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { shareReplay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AuditObsDTO } from '../models/auditObsDTO';
import { AuditParaDTO } from '../models/auditParaDTO';

@Injectable()
export class Step4Service {

  public static BASE_URL = environment.serverUrl;
  public static STEP4_BASE_URL = Step4Service.BASE_URL + '/audit/internal-audit/step4';
  public static CREATE_UPDATE_STEP4_OBS_URL = Step4Service.STEP4_BASE_URL + '/obs';
  public static CREATE_UPDATE_STEP4_PARA_URL = Step4Service.STEP4_BASE_URL + '/para';
  public static GET_AUDIT_PARA_BY_ID_URL = Step4Service.STEP4_BASE_URL + '/para-id';
  public static GET_AUDIT_OBS_BY_ID_URL = Step4Service.STEP4_BASE_URL + '/obs-id';
  public static UPLOAD_FILE_URL = Step4Service.STEP4_BASE_URL + '/file/upload';
  public static GET_FILE_URL = Step4Service.STEP4_BASE_URL + '/file';


  constructor(private http: HttpClient) { }

  createUpdateAuditObs(id: number, reqBody: AuditObsDTO) {
    return this.http.put(Step4Service.CREATE_UPDATE_STEP4_OBS_URL + '/' + id, reqBody)
  }

  createUpdateAuditPara(id: number, reqBody: AuditParaDTO) {
    return this.http.put(Step4Service.CREATE_UPDATE_STEP4_PARA_URL + '/' + id, reqBody)
  }

  getDetailAuditInfo(id: number) {
    return this.http.get(Step4Service.STEP4_BASE_URL + '/' + id).pipe(shareReplay(2));
  }

  getAuditParaFile(id: number) {
    return this.http.get(Step4Service.GET_FILE_URL + '/' + id, { responseType: 'blob' });
  }

  uploadFile(file: File, id: string, paraInfoId: string) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('iaId', id);
    formdata.append('paraInfoId', paraInfoId)
    return this.http.post(Step4Service.UPLOAD_FILE_URL,
      formdata
    )
  }

  getAuditObsList(id: number) {
    return this.http.get(Step4Service.CREATE_UPDATE_STEP4_OBS_URL + '/' + id)
  }

  getAuditObsByid(auditObsId: number, iaId: number) {
    return this.http.get(Step4Service.GET_AUDIT_OBS_BY_ID_URL + `/${auditObsId}/ia/${iaId}`)
  }
  getAuditParaByid(auditParaId: number, iaId: number) {
    return this.http.get(Step4Service.GET_AUDIT_PARA_BY_ID_URL + `/${auditParaId}/ia/${iaId}`)
  }
  getAuditParaList(id: number) {
    return this.http.get(Step4Service.CREATE_UPDATE_STEP4_PARA_URL + '/' + id)
  }

}
