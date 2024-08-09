import { Injectable, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { BehaviorSubject, of } from 'rxjs';
import { DateRangeDTO } from '../sharedFilters/date-range-dto';

@Injectable({
  providedIn: 'root'
})
export class RouteService {
  routeArray: ActivatedRoute[] = [];
  dateRange: DateRangeDTO = {
    startDate: this.getDateInDDMMYYYY(new Date()),
    endDate: this.getEndDateInDDMMYYYY(new Date())
  }
  grList!: Array<any>
  geographyRouteList = of(this.grList)
  date$ = new BehaviorSubject(this.dateRange)
  geography$ = new BehaviorSubject({level: 'state', particular: 'Bihar', id: -1})
  constructor() {
  }

  getDateInDDMMYYYY(date: Date) {
    return moment(date).format('DD-MM-YYYY');
  }

  getEndDateInDDMMYYYY(date: Date) {
    date.setFullYear(date.getFullYear() - 1)
    return moment(date).format('DD-MM-YYYY');
  }
}
