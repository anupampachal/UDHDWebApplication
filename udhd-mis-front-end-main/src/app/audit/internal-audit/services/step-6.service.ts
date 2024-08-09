import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable()
export class Step6Service {

  public static BASE_URL = environment.serverUrl;
  public static STEP6_PART_A_BASE_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-a';
  public static STEP6_PART_A_FILE_UPLOAD_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-a-file';
  public static GET_PART_A_LINE_ITEM_BASE_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-a-obs';
  public static STEP6_PART_A_OTHER_TYPE_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-a-others';

  public static STEP6_PART_B_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b';
  public static STEP6_CREATE_PART_B_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b/obs';
  public static STEP6_GET_PART_B_LIST_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b/irList';
  public static STEP6_PART_B_UPLOAD_FILE_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b/audit-obs-file';
  public static STEP6_PART_B_F_CREATE_UPDATE_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-vat';
  public static STEP6_PART_B_F_GET_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-vat-main';
  public static STEP6_PART_B_F_OTHER_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-vat-others/id';



  public static STEP6_PART_C_URL = Step6Service.BASE_URL + '/audit/internal-audit/step6/part-c';

  constructor(private http: HttpClient) { }

  createUpdateAuditObsPartA(reqBody: any) {
    return this.http.put(Step6Service.STEP6_PART_A_BASE_URL, reqBody)
  }
  getAuditObsById(id: number, iaId: number, partAtype: string) {
    return this.http.post(Step6Service.STEP6_PART_A_BASE_URL + `/${id}/${iaId}?partAType=${partAtype}`, null)
  }
  deleteAuditObsById(id: number, iaId: number, partAtype: string) {
    return this.http.delete(Step6Service.STEP6_PART_A_BASE_URL + `/${id}/obs/${iaId}?partAType=${partAtype}`)
  }

  uploadFileForLineItem(partAtype: string, id: number, iaId: number, file: File) {
    const formdata: FormData = new FormData()
    formdata.append('id', id.toString())
    formdata.append('iaId', iaId.toString())
    formdata.append('file', file);
    return this.http.put(Step6Service.STEP6_PART_A_FILE_UPLOAD_URL, formdata, {
      params: new HttpParams().set('partAType', partAtype)
    })
  }

  getFileById(id: number, type: string) {
    return this.http.get(Step6Service.STEP6_PART_A_FILE_UPLOAD_URL + `/${id}?partAType=${type}`, { responseType: 'blob' })
  }

  createPartBbyType(id: number, type: string, reqBody:string) {
    return this.http.put(Step6Service.STEP6_PART_A_BASE_URL + `/${id}`, reqBody, {
      params: new HttpParams().set('partBType', type)
    })
  }

  getAuditObsPartAlineItemByType(id: number, type: string) {
    return this.http.get(Step6Service.GET_PART_A_LINE_ITEM_BASE_URL + `/${id}?partAType=${type}`)
  }

  getOtherTypeObjective(iaId: number) {
    return this.http.get(Step6Service.STEP6_PART_A_OTHER_TYPE_URL + '/' +  iaId)
  }

  createPartC(reqBody: any) {
  return this.http.put(Step6Service.STEP6_PART_C_URL, reqBody)
  }

  getPartC(id: number, type: string) {
    return this.http.get(Step6Service.STEP6_PART_C_URL + `/${id}?partCEnum=${type}`)
  }

  createUpdatePartB(reqBody: any) {
    return this.http.put(Step6Service.STEP6_CREATE_PART_B_URL, reqBody)
  }

  getPartBbyId(id: number) {
    return this.http.get(Step6Service.STEP6_CREATE_PART_B_URL + '/'  + id)
  }

  getPartBlistByType(id: number, type: string) {

    return this.http.get(Step6Service.STEP6_GET_PART_B_LIST_URL + `/${id}?type=${type}`)
  }

  uploadFilePartB(id: number, iaId: number, file: File) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);

    return this.http.put(Step6Service.STEP6_PART_B_UPLOAD_FILE_URL + `/${id}/ia/${iaId}`, formdata, {responseType: 'text'})
  }

  downloadFile(id: number) {
    return this.http.get( Step6Service.BASE_URL +  '/audit/internal-audit/step6/part-b-file/' + id, {responseType: 'blob'})
  }

  createUpdatePartBF(reqBody: any) {
    return this.http.put(Step6Service.STEP6_PART_B_F_CREATE_UPDATE_URL, reqBody)
  }

  getPartBFTDSVatMain(iaId: number, query: string) {
    return this.http.get(Step6Service.STEP6_PART_B_F_GET_URL + `/${iaId}?type=${query}`)
  }

  getPartBFOtherTypeById(id: number, iaId: number, type: string) {
    return this.http.get(Step6Service.STEP6_PART_B_F_CREATE_UPDATE_URL + `/${id}/ia/${iaId}?type=${type}`)
  }

  getPartBFOtherTypeList(iaId: number) {
    return this.http.get(Step6Service.STEP6_PART_B_F_CREATE_UPDATE_URL + '/' + iaId)
  }

  getPartBfMain(iaId: number, type:string) {
    return this.http.get(Step6Service.STEP6_PART_B_F_GET_URL + `/${iaId}?type=${type}`)
  }
  deletePartBfOther(id: number, iaId: number) {
    return this.http.delete(Step6Service.STEP6_PART_B_F_OTHER_URL + `/${iaId}/others/${id}`)
  }

  uploadPartBfFile(id: number,iaId: number, type: string, file: File) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    return this.http.put(Step6Service.STEP6_PART_B_F_CREATE_UPDATE_URL + `/upload/${id}/ia/${iaId}?type=${type}`, formdata)
  }

  downloadPartBfFile(fileId: string) {
    return this.http.get(Step6Service.STEP6_PART_B_F_CREATE_UPDATE_URL + '/download/' + fileId, {responseType: 'blob'})
  }

  createUpdateBH(reqBody: any) {
    return this.http.put(Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-utilisation', reqBody)
  }

  getPartBH(id: number, iaId: number) {
    return this.http.get(Step6Service.BASE_URL + `/audit/internal-audit/step6/part-b-utilisation/${id}/ia/${iaId}`)
  }

  getPartBHFile(iaId: number) {
    return this.http.get(Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-utilisation/file/' + iaId, {responseType: 'blob'})
  }

  deletePartBHItem(id: number, iaId: number) {
    return this.http.delete(Step6Service.BASE_URL + `/audit/internal-audit/step6/part-b-utilisation/${id}/ia/${iaId}`)
  }

  getAllPartBHLineItems(iaId: number) {
    return this.http.get(Step6Service.BASE_URL + `/audit/internal-audit/step6/part-b-utilisation/ia/${iaId}`)
  }

  uploadPartBHFile(iaId: number, file:File) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    return this.http.put(Step6Service.BASE_URL + '/audit/internal-audit/step6/part-b-utilisation/file/' + iaId, formdata)
  }
}


