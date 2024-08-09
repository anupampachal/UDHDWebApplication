import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserMgtService } from 'src/app/settings/user-mgt/services/user-mgt.service';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { RouteService } from '../../services/route.service';

@Component({
  selector: 'app-ulb-filter',
  templateUrl: './ulb-filter.component.html',
  styleUrls: ['./ulb-filter.component.css']
})
export class DashboardUlbFilterComponent implements OnInit {

  divisions: any;
  districts: any;
  ulbType: string[] = []
  ulbList: any;
  ulbFilterForm!: FormGroup;
  displaySelectedFilter = ''
  ulbFilterTypes = ["MUNICIPAL_CORPORATION", "MUNICIPAL_COUNCIL", "NAGAR_PANCHAYAT"]
  ulbTypeForm!: FormGroup;
  @Output() clearDataEvent = new EventEmitter<PaginationModel>();
  @Output() filteredData = new EventEmitter<PaginationModel>();
  @Output() searchByDivision = new EventEmitter()
  sortingDirection = true;
  currentlySelectedVal = '';
  filterdata = '';
  ulbTypeFilter = ""
  constructor(
    private service: UserMgtService,
    private fb: FormBuilder,
    private routerService: RouteService
  ) { }

  ngOnInit(): void {
    this.service.getDivisionList().subscribe(divisions => this.divisions = divisions)
    this.ulbFilterForm = this.fb.group({
      division: [null,],
      district: [null, ],
      ulbType: [null, ],
      ulb: [null, ],
    })
    this.ulbTypeForm = this.fb.group({
      utype: []
    })
  }

  getDistricts() {
    const divisionId = this.ulbFilterForm.value.division.id
    this.service.getDistrictListByDivisionId(divisionId).subscribe(districts => this.districts = districts)
  }

  getUlbType() {
    this.ulbType = ["MUNICIPAL_CORPORATION", "MUNICIPAL_COUNCIL", "NAGAR_PANCHAYAT"];

  }
  getUlbList() {
    const districtId = this.ulbFilterForm.value.district.id
    const ulbName = this.ulbFilterForm.value.ulbType
    this.service.getULBListByDistrictId(districtId, ulbName).subscribe(ulbs => {
      this.ulbList = ulbs
      
    })
  }

  clearData() {
    this.filterdata = '';
    const page = this.getPageModel(
      'ALL',
      0,
      10,
      this.currentlySelectedVal,
      this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
      this.filterdata
    );
    this.clearDataEvent.emit(page);
  }

  onFilterData(data: string) {
    this.filterdata = data;
    const page = this.getPageModel(
      'ALL',
      0,
      10,
      this.currentlySelectedVal,
      this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
      this.filterdata
    );
    this.filteredData.emit(page);
  }

  getPageModel(
    criteria: string,
    pageNo: number,
    pageSize: number,
    sortedBy: string,
    directionOfSort: string,
    queryString: string
  ): PaginationModel {
    const page = new PaginationModel();
    page.criteria = criteria;
    page.pageNo = pageNo;
    page.pageSize = pageSize;
    page.sortedBy = sortedBy;
    page.directionOfSort = directionOfSort;
    if (queryString != '') {
      page.queryString = queryString;
    }
    return page;
  }

  searchByUlbType() {
   
  }
  filterByDivision() {
    this.searchByDivision.emit(this.ulbFilterForm.value)
  }

  initializeForm() {
    this.ulbFilterForm.reset()
  }
  navigateState() {
      this.routerService.geography$.next({level: 'state', particular: 'Bihar', id: -1})
  }

}
