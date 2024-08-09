import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { shareReplay } from 'rxjs/operators';
import { GenericResponseObject } from '../shared/model/generic-response-object-pagination.model';
import { PaginationModel } from '../shared/model/pagination-request.model';
import { ActivitiesRouteConstants } from './activities-route-constants';
import { EventsNMeetingsDTO } from './events-and-meetings/events-nmeetings-dto';
import { SendEmailDTO } from './models/send-email-notification.model';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {
  constructor(private http: HttpClient) { }

  getHolidayByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(ActivitiesRouteConstants.ACTIVITIES_BASE_URL, data).pipe(shareReplay(2));
  }

  findHolidayById(id: number): Observable<any> {
    return this.http.get<any>(ActivitiesRouteConstants.ACTIVITIES_BASE_URL + "/" + id);
  }
  deleteHolidayById(id?: number) {
    if (!!id) {
      return this.http.delete(ActivitiesRouteConstants.ACTIVITIES_BASE_URL + "/" + id);
    }
    return of(null);
  }




  getMeetingsByPage(data: PaginationModel): Observable<GenericResponseObject<any>> {
    return this.http.post<GenericResponseObject<any>>(ActivitiesRouteConstants.MEETINGS_BASE_URL, data).pipe(shareReplay(2));
  }

  findMeetingsById(id: number): Observable<any> {
    return this.http.get<any>(ActivitiesRouteConstants.MEETINGS_BASE_URL + "/" + id);
  }

  deleteMeetingById(id?: number) {
    if (!!id) {
      return this.http.delete(ActivitiesRouteConstants.MEETINGS_BASE_URL + "/" + id);
    }
    return of(null);
  }

  createEventAndMeeting(requestData: EventsNMeetingsDTO): Observable<EventsNMeetingsDTO> {
    return this.http.post<EventsNMeetingsDTO>(ActivitiesRouteConstants.MEETINGS_BASE_URL, requestData);
  }

  sendEmail(emailDTO: SendEmailDTO): Observable<boolean> {
    return this.http.put<boolean>(ActivitiesRouteConstants.SEND_EMAIL_NOTIFICATION_URL, emailDTO);
  }
}
