import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { environment } from 'src/environments/environment';
import { HistoricalDataDTO } from '../financial-data-uploads/historical-data-upload/historical-data-dto';

@Injectable()
export class HistoricalDataUploadService {

  constructor(
    private http: HttpClient
  ) { }

  public static BASE_URL = environment.serverUrl;
  public static HISTORICAL_DATA_BASE_URL = HistoricalDataUploadService.BASE_URL + '/deas/historic-upload';
  public static UPLOAD_HISTORICAL_DATA_URL = HistoricalDataUploadService.HISTORICAL_DATA_BASE_URL + '/file/upload';
  public static GET_ULB_FOR_HISTORICAL_DATA_URL = HistoricalDataUploadService.HISTORICAL_DATA_BASE_URL + '/ulb';
  public static GET_HISTORICAL_DATA_BY_PAGE_URL = HistoricalDataUploadService.HISTORICAL_DATA_BASE_URL + '/info';
  public static GET_HISTORICAL_DATA_FILE_URL = HistoricalDataUploadService.HISTORICAL_DATA_BASE_URL + "/file";

  findHistoricalDataById(id: string): Observable<HistoricalDataDTO> {
    return this.http.get<HistoricalDataDTO>(HistoricalDataUploadService.HISTORICAL_DATA_BASE_URL + "/" + id);
  }

  getHistoricalDataByPage(data: PaginationModel): Observable<GenericResponseObject<HistoricalDataDTO>> {
    return this.http.post<GenericResponseObject<HistoricalDataDTO>>(HistoricalDataUploadService.GET_HISTORICAL_DATA_BY_PAGE_URL, data).pipe(shareReplay(2));
  }
  getULBForHistoricData(): Observable<ULBDTO> {
    return this.http.get<ULBDTO>(HistoricalDataUploadService.GET_ULB_FOR_HISTORICAL_DATA_URL)
  }
  uploadHistoricalData(file: File, histData: HistoricalDataDTO) {
    const formdata: FormData = new FormData()
    formdata.append('file', file);
    formdata.append('startDate', histData.startDate);
    formdata.append('endDate', histData.endDate);
    formdata.append('ulbId', histData.ulbId.toString());
    return this.http.post<HistoricalDataDTO>(HistoricalDataUploadService.UPLOAD_HISTORICAL_DATA_URL,
      formdata, {
      reportProgress: true,
    }
    );
  }
  getFileByFileId(fileId: string) {
    return this.http.get(HistoricalDataUploadService.GET_HISTORICAL_DATA_FILE_URL + '/' + fileId, { responseType: 'blob' });
  }
}
