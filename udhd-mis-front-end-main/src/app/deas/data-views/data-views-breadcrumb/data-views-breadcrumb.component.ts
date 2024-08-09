import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { DataViewsBreadcrumbModel } from '../../models/dataViews-breadcrumbModel';
import { GeoDivisionResponseDTO } from '../../models/geo-division-response-dto';
import { RouteService } from '../../services/route.service';
import { DateRangeDTO } from '../../sharedFilters/date-range-dto';

@Component({
  selector: 'app-data-views-breadcrumb',
  templateUrl: './data-views-breadcrumb.component.html',
  styleUrls: ['./data-views-breadcrumb.component.css']
})
export class DataViewsBreadcrumbComponent implements OnInit {

  @Input() routeArr!: ActivatedRoute[];
  breadcrumbItems: DataViewsBreadcrumbModel[] = [];
  displayDateFilter = false;
  displayUlbFilter = false;
  constructor(private route: ActivatedRoute, private router: Router, private routeService: RouteService) { }

  ngOnInit(): void {

    var pathStr = "";
    for (var i = 1; i < this.routeArr.length; i++) {
      if (this.routeArr[i].snapshot.url && this.routeArr[i].snapshot.url[0] && this.routeArr[i].snapshot.url[0].path !== '') {
        pathStr = pathStr + "/" + this.routeArr[i].snapshot.url[0];
        if (!!this.routeArr[i].snapshot.data && this.routeArr[i].snapshot.data.breadcrumb) {
          const breadcrumb = new DataViewsBreadcrumbModel();
          breadcrumb.label = this.routeArr[i].snapshot.data.breadcrumb;
          breadcrumb.route = pathStr;
          this.breadcrumbItems.push(breadcrumb);
        }
      }
    }
  }
  ulbFilter() {
    this.displayUlbFilter = !this.displayUlbFilter;
  }

  dateFilter() {
    this.displayDateFilter = !this.displayDateFilter;
  }

  clearDataEvent(data: PaginationModel) {
   
  }

  filterByDivision(divisionFilterValue: any) {
    // this.divisionHierarchy = divisionFilterValue
    
  }
  currentDivision(division: GeoDivisionResponseDTO) {
    this.routeService.geography$.next({
      level: 'division',
      particular: division.name,
      id: division.id
    })}
  getDateRangeValue($event: DateRangeDTO) {
    this.routeService.date$.next($event)
  }

  filteredDataEvent(data: PaginationModel) {
    
  }

}
