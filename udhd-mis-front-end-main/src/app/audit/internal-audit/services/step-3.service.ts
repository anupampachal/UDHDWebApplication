import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Step3AdminDTO } from '../models/step3AdminDTO';

@Injectable()
export class Step3Service {

  public static BASE_URL = environment.serverUrl;
  public static STEP3_BASE_URL = Step3Service.BASE_URL + '/audit/internal-audit/step3'

  constructor(private http: HttpClient) { }

  findIaAdminById(id: number): Observable<any> {
    return this.http.get<any>(Step3Service.STEP3_BASE_URL + "/" + id);
  }

  createAdministration(requestData: Step3AdminDTO) {
    return this.http.post(Step3Service.STEP3_BASE_URL, requestData)
  }

  createUpdateMember(id: number, requestData: any) {
    return this.http.put(Step3Service.STEP3_BASE_URL + '/' + id, requestData)
  }

  getMemberListByType(id: number, type: string) {
    return this.http.get(Step3Service.STEP3_BASE_URL + `/member/${id}?type=${type}`)
  }

  getMemberById(id: number, iaId: number, type: string) {
    return this.http.get(Step3Service.STEP3_BASE_URL + `/member/mem/${id}/ia/${iaId}?type=${type}`)
  }

  deleteMemberById(id: number, iaId: number, type: string) {
    return this.http.delete(Step3Service.STEP3_BASE_URL + `/member/mem/${id}/ia/${iaId}?type=${type}`)
  }
}
