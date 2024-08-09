import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DateRangeDTO } from 'src/app/deas/sharedFilters/date-range-dto';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { BudgetUploadDTO } from '../../models/budget-upload-dto';
import { BudgetUploadService } from '../../services/budget-upload.service';

@Component({
  selector: 'app-list-budget-upload',
  templateUrl: './list-budget-upload.component.html',
  styleUrls: ['./list-budget-upload.component.css']
})
export class ListBudgetUploadComponent implements OnInit {

  paginationTableData$: Observable<MatTableWithPagination<BudgetUploadDTO>> =
    of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  showDateFilter = false;

  showUlbFilter = false;
  displayTable = false;
  currentlySelectedVal = '';
  sortingDirection = true;
  filterdata = '';
  sortOptions: SortOptionModel[] = [
    {
      displayName: '',
      value: ''
    },
  ];
  @ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.service
      .getBudgetByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.service
      .getBudgetByPage(data)
      .pipe(map(this.setTableData));
  }
  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.service
      .getBudgetByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.service
      .getBudgetByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: BudgetUploadDTO) {
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
    this.paginationTableData$ = this.service
      .getBudgetByPage(page)
      .pipe(map(this.setTableData));
  }



  constructor(
    private router: Router,
    private service: BudgetUploadService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.paginationTableData$ = this.service
      .getBudgetByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'updatedOn',
          'DESCENDING',
          this.filterdata
        ))
      .pipe(map(this.setTableData));
    this.routeArray = this.route.pathFromRoot;
  }

  setTableData(data: GenericResponseObject<BudgetUploadDTO>) {
    const matTableData = new MatTableWithPagination<BudgetUploadDTO>();
    matTableData.cols = [
      'inputDate',
      'ulbName',
      'overWritten',
      'status',
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'inputDate',
      'ulbName',
      'overWritten',
      'status',
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
  dateFilter() {
    this.showDateFilter = !this.showDateFilter
  }

  ulbFilter() {
    this.showUlbFilter = !this.showUlbFilter
  }

  filterByDivision(divisionFilterValue: any) {
    
  }

  getDateRangeValue($event: DateRangeDTO) {
    
  }

  displayTableForDateRange(event: DateRangeDTO) {
    this.displayTable = true
  }

  exportTableToPdf() {
    let dataSrc: any[][] = []
    let i = 0;
    this.paginationTableData$.subscribe(data => data.data.filteredData.forEach((obj:any)=> {
      let arr = []
      arr.push(i+1)
      arr.push(obj.inputDate, obj.ulbName, obj.overWritten, obj.status)
      dataSrc.push(arr)
      i = i+1
    }))

    this.dmt.exportToPdf(
      [["Srl No.",
      'inputDate',
      'ulbName',
      'overWritten',
      'status',]],
      dataSrc,
      ''
      )
  }


  updatedExportToExcel() {
    let excelJsonData:any = []
    let i = 0;
    this.paginationTableData$.subscribe(data => data.data.filteredData.forEach((obj: any)=> {
      let arr = {
       "Srl No.": i+1,
       'inputDate': obj.inputDate,
       'ulbName': obj.ulbName,
       'status':obj.status,
       'overWritten':obj.overWritten.toString(),
      };
      
      excelJsonData.push(arr)
      i = i+1
    }))
    var wscols = [{wch:6},{wch:15},{wch:15},{wch:25},{wch:10},{wch:10}];

    this.dmt.exportAsExcelFile(excelJsonData, 'Budget', wscols)
  }

}
