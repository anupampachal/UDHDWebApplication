import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { GeoDivisionDTO } from 'src/app/shared/model/geo-division.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { GeoDivisionService } from '../services/geo-division.service';

@Component({
  selector: 'app-list-division',
  templateUrl: './list-division.component.html',
  styleUrls: ['./list-division.component.css']
})
export class ListDivisionComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<GeoDivisionDTO>> =
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
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(data)
    .pipe(map(this.setTableData));
}
filteredDataEvent(data: PaginationModel) {
  this.filterdata = data && data.queryString ? data.queryString : '';
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(data)
    .pipe(map(this.setTableData));
}

getSortedByEvent(data: PaginationModel) {
  this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(data)
    .pipe(map(this.setTableData));
}
sortingDirectionChangeEvent(data: PaginationModel) {
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(data)
    .pipe(map(this.setTableData));
}
showView($event: GeoDivisionDTO) {
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
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(page)
    .pipe(map(this.setTableData));
}

exportTableToPdf() {
  let dataSrc: any[][] = []
  let i = 0;
  this.paginationTableData$.subscribe(data => data.data.filteredData.forEach(obj => {
    let arr = []
    arr.push(i+1)
    arr.push(obj.name, obj.code, obj.active)
    dataSrc.push(arr)
    i = i+1
  }))

  this.dmt.exportToPdf(
    [["Srl No.", "Name", "Code", "Active"]],
    dataSrc,
    '                                             Division List'
    )
}


updatedExportToExcel() {
  let excelJsonData:any = []
  let i = 0;
  this.paginationTableData$.subscribe(data => data.data.filteredData.forEach(obj => {
    let arr = {
     "Srl No.": i+1,
     "Name": obj.name,
     "Code": obj.code,
     "Active": obj.active.toString()
    };
    
    excelJsonData.push(arr)
    i = i+1
  }))
  var wscols = [{wch:6},{wch:20},{wch:15},{wch:15},{wch:6}];

  this.dmt.exportAsExcelFile(excelJsonData, 'division', wscols)
}

exportTableToSpreadsheet() {
  this.dmt.exportToCSV([3,4])
}
constructor(
  private router: Router,
  private geoDivisionService: GeoDivisionService,
  private route: ActivatedRoute
) { }

ngOnInit() {
  this.paginationTableData$ = this.geoDivisionService
    .getGeoDivisionByPage(
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

setTableData(data: GenericResponseObject<GeoDivisionDTO>) {
  const matTableData = new MatTableWithPagination<GeoDivisionDTO>();
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


}
