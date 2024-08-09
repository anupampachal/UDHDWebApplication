import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { AFSDTO } from '../models/afs-dto';

@Injectable({
  providedIn: 'root'
})
export class FinancialStatementsService {

  constructor(
    private http: HttpClient
  ) { }

  public static BASE_URL = environment.serverUrl;
  public static AFS_DATA_BASE_URL = FinancialStatementsService.BASE_URL + '/deas/annual-finance';
  public static UPLOAD_AFS_DATA_URL = FinancialStatementsService.AFS_DATA_BASE_URL + '/file/upload';
  public static GET_ULB_FOR_AFS_DATA_URL = FinancialStatementsService.AFS_DATA_BASE_URL + '/ulb';
  public static GET_AFS_DATA_BY_PAGE_URL = FinancialStatementsService.AFS_DATA_BASE_URL + '/info';
  public static GET_AFS_DATA_FILE_URL = FinancialStatementsService.AFS_DATA_BASE_URL + "/file";

  findAFSDataById(id: string): Observable<AFSDTO> {
    return this.http.get<AFSDTO>(FinancialStatementsService.AFS_DATA_BASE_URL + "/" + id);
  }

  getAFSDataByPage(data: PaginationModel) {
    return this.http.post(FinancialStatementsService.GET_AFS_DATA_BY_PAGE_URL, data).pipe(shareReplay(2));
  }
  getULBForAFS(): Observable<ULBDTO> {
    return this.http.get<ULBDTO>(FinancialStatementsService.GET_ULB_FOR_AFS_DATA_URL)
  }
  uploadAFSData(file: File, afsData: AFSDTO) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('startDate', afsData.startDate);
    formdata.append('endDate', afsData.endDate);
    formdata.append('ulbId', afsData.ulbId.toString());
    return this.http.post<AFSDTO>(FinancialStatementsService.UPLOAD_AFS_DATA_URL,
      formdata, {
      reportProgress: true,
    }
    );
  }
  getFileByFileId(fileId: string) {
    return this.http.get(FinancialStatementsService.GET_AFS_DATA_FILE_URL + '/' + fileId, { responseType: 'blob' });
  }
}
