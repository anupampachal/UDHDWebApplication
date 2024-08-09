import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserInfoDTO } from 'src/app/settings/model/user-mgt.model';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { UserMgtService } from '../../services/user-mgt.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  constructor(
    private router: Router,
    private userMgtService: UserMgtService,
    private route: ActivatedRoute
  ) { }
  paginationTableData$: Observable<MatTableWithPagination<UserInfoDTO>> =
    of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = 'name';
  sortingDirection = true;
  filterdata = '';
  sortOptions: SortOptionModel[] = [
    {
      displayName: 'Name',
      value: 'name'
    }, {
      displayName: 'Username',
      value: 'username'
    }
  ];
  @ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: UserInfoDTO) {
    this.router.navigate(['../view/', $event.id], {
      relativeTo: this.route,
    });
  }

  goToCreate(param: string) {
    this.router.navigate(['../create/', param], {
      relativeTo: this.route,
    });
  }

  changeDataForPagination($event: PaginatorModel) {

    const page = this.getPageModel(
      'ALL',
      $event.currentPage,
      $event.pageSize,
      this.currentlySelectedVal,
      this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
      this.filterdata
    );
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(page)
      .pipe(map(this.setTableData));
  }




  ngOnInit() {
    this.paginationTableData$ = this.userMgtService
      .getUserListByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          this.currentlySelectedVal,
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        ))
      .pipe(map(this.setTableData));
    this.routeArray = this.route.pathFromRoot;
  }

  setTableData(data: GenericResponseObject<UserInfoDTO>) {
    const matTableData = new MatTableWithPagination<UserInfoDTO>();
    matTableData.cols = [
      'name',
      'mobileNo',
      'authority',
      'enabled',
      'username',
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'name',

      'mobileNo',
      'authority',
      'enabled',
      'username',
      'actions'
    ];
    matTableData.pageSizeOptions = [10, 20, 50, 100];
    matTableData.data = new MatTableDataSource(data.currentRecords.content);
    matTableData.dataTotLength = data.totalRecords;
    matTableData.pageSize = data.itemsPerPage;
    matTableData.currentPage = data.currentPage;
    return matTableData;
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



  filterAndSort() {
    this.showFilter = !this.showFilter;
    if (!this.showFilter) {
      this.currentlySelectedVal = '';
    }
  }

  exportTableToPdf() {
    let dataSrc: any[][] = []
    let i = 0;
    this.paginationTableData$.subscribe(data => data.data.filteredData.forEach(obj => {
      let arr = []
      arr.push(i+1)
      arr.push(obj.name,obj.username, obj.authority, obj.mobileNo, obj.enabled)
      dataSrc.push(arr)
      i = i+1
    }))

    this.dmt.exportToPdf(
      [["Srl No.", "Name","User Name", "Authority", "Mobile No.","Enabled"]],
      dataSrc,
      '                                             Users List'
      )
  }
  exportTableToSpreadsheet() {
    this.dmt.exportToCSV([4,6])
  }


}
