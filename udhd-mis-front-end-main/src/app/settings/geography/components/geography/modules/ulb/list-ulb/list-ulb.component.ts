import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { ULBService } from '../services/ulb.service';

@Component({
  selector: 'app-list-ulb',
  templateUrl: './list-ulb.component.html',
  styleUrls: ['./list-ulb.component.css']
})
export class ListUlbComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<ULBDTO>> =
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
    displayName: 'Code',
    value: 'code'
  }
];
@ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;

clearDataEvent(data: PaginationModel) {
  this.filterdata = '';
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(data)
    .pipe(map(this.setTableData));
}
filteredDataEvent(data: PaginationModel) {
  this.filterdata = data && data.queryString ? data.queryString : '';
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(data)
    .pipe(map(this.setTableData));
}

getSortedByEvent(data: PaginationModel) {
  this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(data)
    .pipe(map(this.setTableData));
}
sortingDirectionChangeEvent(data: PaginationModel) {
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(data)
    .pipe(map(this.setTableData));
}
showView($event: ULBDTO) {
  this.router.navigate(['../view/', $event.id], {
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
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(page)
    .pipe(map(this.setTableData));
}



constructor(
  private router: Router,
  private ulbService: ULBService,
  private route: ActivatedRoute
) { }

ngOnInit() {
  this.paginationTableData$ = this.ulbService
    .getULBDataByPage(
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

setTableData(data: GenericResponseObject<ULBDTO>) {
  const matTableData = new MatTableWithPagination<ULBDTO>();
  matTableData.cols = [
    'name',
    'code',
    'active',
  ];
  matTableData.colsWithAction = [
    'srlNo',

    'name',
    'code',
    'active',
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
    arr.push(obj.name, obj.code, obj.district.name, obj.active)
    dataSrc.push(arr)
    i = i+1
  }))

  this.dmt.exportToPdf(
    [["Srl No.", "Name", "Code", "District","Active"]],
    dataSrc,
    '                                             ULB List'
    )
}
exportTableToSpreadsheet() {
  this.dmt.exportToCSV([3,4])
}

updatedExportToExcel() {
  let excelJsonData:any = []
  let i = 0;
  this.paginationTableData$.subscribe(data => data.data.filteredData.forEach(obj => {
    let arr = {
     "Srl No.": i+1,
     "Name": obj.name,
     "Code": obj.code,
     "District": obj.district.name,
     "Active": obj.active.toString()
    };
    
    excelJsonData.push(arr)
    i = i+1
  }))
  var wscols = [{wch:6},{wch:25},{wch:20},{wch:15},{wch:6}];

  this.dmt.exportAsExcelFile(excelJsonData, 'ulb', wscols)
}
}
