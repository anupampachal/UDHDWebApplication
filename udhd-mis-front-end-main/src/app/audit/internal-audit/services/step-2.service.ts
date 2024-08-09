import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { environment } from 'src/environments/environment';
import { COAKtypeDTO } from '../models/coakTypeDTO';
import { SworFindByIdDTO } from '../models/sworFindByIdDTO';
import { BooleanResponseDTO } from 'src/app/shared/model/boolean-response.model';

@Injectable()
export class Step2Service {

  public static BASE_URL = environment.serverUrl;
  public static STEP2_BASE_URL = Step2Service.BASE_URL + '/audit/internal-audit/step2';
  public static CREATE_UPDATE_STEP2_INTRO_URL = Step2Service.STEP2_BASE_URL + '/introduction';
  public static CREATE_UPDATE_SWOR = Step2Service.STEP2_BASE_URL + '/swor';
  public static DELETE_SWOR = Step2Service.STEP2_BASE_URL + '/swor-del';
  public static GET_STRENGTH_WEAKNESS_RECOM = Step2Service.STEP2_BASE_URL + '/swor/info';
  public static CREATE_UPDATE_COACK = Step2Service.STEP2_BASE_URL + '/coack';
  public static UPLOAD_FILE_URL = Step2Service.STEP2_BASE_URL + '/file/upload';
  public static DOWNLOAD_FILE_URL = Step2Service.STEP2_BASE_URL + '/file';

  public static FIND_SWOR_BY_ID_URL = Step2Service.STEP2_BASE_URL + '/swor/info';
  public static GET_COACK_BY_TYPE = Step2Service.STEP2_BASE_URL + '/coack/info';


  constructor(private http: HttpClient) { }


  getStep2SworByPage(data: any): Observable<any> {
    return this.http.post<any>(Step2Service.GET_STRENGTH_WEAKNESS_RECOM, data).pipe(shareReplay(2));
  }

  createUpdateStep2Intro(requestData: any): Observable<any> {
    return this.http.post<any>(Step2Service.CREATE_UPDATE_STEP2_INTRO_URL, requestData);
  }

  createUpdateSwor(requestData: any): Observable<any> {
    return this.http.post<any>(Step2Service.CREATE_UPDATE_SWOR, requestData)
  }

  deleteSwor(requestData:any):Observable<BooleanResponseDTO>{
    return this.http.post<BooleanResponseDTO>(Step2Service.DELETE_SWOR,requestData);
  }
  createUpdateCoack(requestData: any): Observable<any> {
    return this.http.post<any>(Step2Service.CREATE_UPDATE_COACK, requestData)
  }
  uploadBudget(file: File, id: string) {

    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('ia', id)
    return this.http.post(Step2Service.UPLOAD_FILE_URL,
      formdata
    )
  }
  findSworById(sworData: SworFindByIdDTO) {
    return this.http.post(Step2Service.FIND_SWOR_BY_ID_URL, sworData)
  }
  getExSummaryByIaId(id: number) {
    return this.http.get(Step2Service.STEP2_BASE_URL + '/' + id)
  }
  getSworList(id: number, type: string) {
    return this.http.get(Step2Service.CREATE_UPDATE_SWOR + '/' + id, {
      params: new HttpParams()
        .set('type', type)
    })
  }

  getCOAKByType(req: COAKtypeDTO) {

    return this.http.post(Step2Service.GET_COACK_BY_TYPE, req, {responseType: 'text'})
  }

  downloadFile(id: string) {
    return this.http.get(Step2Service.DOWNLOAD_FILE_URL + '/' + id, { responseType: 'blob' })
  }
}
