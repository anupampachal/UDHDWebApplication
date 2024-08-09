import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { environment } from 'src/environments/environment';
import { TicketDTO } from '../ticket-dto';

@Injectable()
export class SupportService {

  constructor(private http: HttpClient) { }

  public static BASE_URL = environment.serverUrl
  public static SUPPORT_BASE_URL = SupportService.BASE_URL + "/support";
  public static STNG_FIND_TICKET_BY_ID_URL = SupportService.SUPPORT_BASE_URL;
  public static STNG_GET_TICKET_BY_PAGE_URL = SupportService.SUPPORT_BASE_URL + "/info";
  public static STNG_CREATE_UPDATE_TICKET_URL = SupportService.SUPPORT_BASE_URL + "/save";


  getTicketByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(SupportService.STNG_GET_TICKET_BY_PAGE_URL, data).pipe(shareReplay(2));
  }

  findTicketById(id: string): Observable<any> {
    return this.http.get<any>(SupportService.STNG_FIND_TICKET_BY_ID_URL + "/" + id);
  }

createUpdateTicket(requestData: TicketDTO): Observable<TicketDTO> {
    return this.http.post<TicketDTO>(SupportService.STNG_CREATE_UPDATE_TICKET_URL, requestData);
  }
}
