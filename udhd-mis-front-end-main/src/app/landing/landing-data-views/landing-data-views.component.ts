import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorityConstants } from 'src/app/authority-constants';
import { NavItem } from 'src/app/shared/model/nav-item.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { GeoDivisionResponseDTO } from '../models/geo-division-response-dto';
import { RouteService } from '../services/route.service';
import { DateRangeDTO } from '../sharedFilters/date-range-dto';

@Component({
  selector: 'app-landing-data-views',
  templateUrl: './landing-data-views.component.html',
  styleUrls: ['./landing-data-views.component.css']
})
export class LandingDataViewsComponent implements OnInit {
  routeArray: ActivatedRoute[] = [];
  currentDateRange!: DateRangeDTO;
  displayDateFilter = false;
  displayUlbFilter = false;
  divisionHierarchy!: any;
  constructor(private route: ActivatedRoute, private router: Router, private routeService: RouteService) {
   }
  ngOnInit() {
    this.routeArray = this.route.pathFromRoot
    this.routeService.date$.subscribe(currDateRange => this.currentDateRange = currDateRange)
  }
  tabs: NavItem[] = [
    {
      displayName: 'Income & Expense',
      iconName: 'trending_up',
      route: 'income-expense',
      iconType: true,
      authorities: [
        AuthorityConstants.ROLE_FLIA,
        AuthorityConstants.ROLE_INTERNAL_AUDITOR,
        AuthorityConstants.ROLE_ULB_ACCOUNTANT,
        AuthorityConstants.ROLE_ULB_CMO,
        AuthorityConstants.ROLE_SLPMU_ADMIN,
        AuthorityConstants.ROLE_SLPMU_ACCOUNT,
        AuthorityConstants.ROLE_SLPMU_AUDIT,
        AuthorityConstants.ROLE_SLPMU_UC,
        AuthorityConstants.ROLE_SLPMU_IT,
        AuthorityConstants.ROLE_UDHD_PSEC,
        AuthorityConstants.ROLE_UDHD_SEC,
        AuthorityConstants.ROLE_UDHD_SO,
        AuthorityConstants.ROLE_UDHD_IT
      ],
    },
    {
      displayName: 'Cash & Bank Balances',
      iconName: 'fa-bank',
      route: 'cash-and-bank-balance',
      iconType: false,
      authorities: [
        AuthorityConstants.ROLE_FLIA,
        AuthorityConstants.ROLE_INTERNAL_AUDITOR,
        AuthorityConstants.ROLE_ULB_ACCOUNTANT,
        AuthorityConstants.ROLE_ULB_CMO,
        AuthorityConstants.ROLE_SLPMU_ADMIN,
        AuthorityConstants.ROLE_SLPMU_ACCOUNT,
        AuthorityConstants.ROLE_SLPMU_AUDIT,
        AuthorityConstants.ROLE_SLPMU_UC,
        AuthorityConstants.ROLE_SLPMU_IT,
        AuthorityConstants.ROLE_UDHD_PSEC,
        AuthorityConstants.ROLE_UDHD_SEC,
        AuthorityConstants.ROLE_UDHD_SO,
        AuthorityConstants.ROLE_UDHD_IT
      ],
    },
    {
      displayName: 'Balance Sheet',
      iconName: ' grid_view',
      route: 'balance-sheet',
      iconType: true,
      authorities: [
        AuthorityConstants.ROLE_FLIA,
        AuthorityConstants.ROLE_INTERNAL_AUDITOR,
        AuthorityConstants.ROLE_ULB_ACCOUNTANT,
        AuthorityConstants.ROLE_ULB_CMO,
        AuthorityConstants.ROLE_SLPMU_ADMIN,
        AuthorityConstants.ROLE_SLPMU_ACCOUNT,
        AuthorityConstants.ROLE_SLPMU_AUDIT,
        AuthorityConstants.ROLE_SLPMU_UC,
        AuthorityConstants.ROLE_SLPMU_IT,
        AuthorityConstants.ROLE_UDHD_PSEC,
        AuthorityConstants.ROLE_UDHD_SEC,
        AuthorityConstants.ROLE_UDHD_SO,
        AuthorityConstants.ROLE_UDHD_IT
      ],
    },
  ];

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


    if(divisionFilterValue.ulb) {
      this.routeService.geography$.next({
        level: 'ulb',
        particular: divisionFilterValue.ulb.name,
        id: divisionFilterValue.ulb.id
      })
      return
    }

    if(divisionFilterValue.district) {
      this.routeService.geography$.next({
        level: 'district',
        particular: divisionFilterValue.district.name,
        id: divisionFilterValue.district.id
      })
      return
    }
if(divisionFilterValue.division) {
  this.routeService.geography$.next({
    level: 'division',
    particular: divisionFilterValue.division.name,
    id: divisionFilterValue.division.id
  })
  return
}

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
  //   this.filterdata = data && data.queryString ? data.queryString : '';
  // this.paginationTableData$ = this.geoDivisionService
  //   .getGeoDivisionByPage(data)
  //   .pipe(map(this.setTableData));
  
  }

}
