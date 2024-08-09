import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FinancialStatementsService } from 'src/app/deas/services/financial-statements.service';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { AFSDTO } from '../../afs-dto';

@Component({
  selector: 'app-list-annual-financial-statement',
  templateUrl: './list-annual-financial-statement.component.html',
  styleUrls: ['./list-annual-financial-statement.component.css']
})
export class ListAnnualFinancialStatementComponent implements OnInit {
    paginationTableData$: Observable<MatTableWithPagination<AFSDTO>> =
    of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  showDateFilter = false;
  showUlbFilter = false;
  currentlySelectedVal = 'name';
  sortingDirection = true;
  filterdata = '';
  @ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;

  sortOptions: SortOptionModel[] = [
    {
      displayName: '',
      value: ''
    },
  ];
  displayTable = false;
  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.service
      .getAFSDataByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.service
      .getAFSDataByPage(data)
      .pipe(map(this.setTableData));
  }
  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.service
      .getAFSDataByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.service
      .getAFSDataByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AFSDTO) {
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
      .getAFSDataByPage(page)
      .pipe(map(this.setTableData));
  }



  constructor(
    private router: Router,
    private service: FinancialStatementsService,
    private route: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.paginationTableData$ = this.service
      .getAFSDataByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          "updatedOn",
          "DESCENDING",
          this.filterdata
        ))
      .pipe(map(this.setTableData));
    this.routeArray = this.route.pathFromRoot;

    this.http.post("http://demo.scsinfinity.com:8080/api/deas/annual-finance/info", {"criteria":"ALL","pageNo":0,"pageSize":10,"sortedBy":"updatedOn","directionOfSort":"DESCENDING"}).subscribe()
  }

  setTableData(data: any) {
    const matTableData = new MatTableWithPagination<AFSDTO>();
    matTableData.cols = [
      'startDate',
      'endDate',
      'ulbName',
      'overWritten',
      'status'
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'startDate',
      'endDate',
      'ulbName',
      'overWritten',
      'status',
      'actions'
    ];
    matTableData.pageSizeOptions = [10, 20, 50, 100];
    matTableData.data = new MatTableDataSource(data.content);
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
  exportTableToPdf() {
    let dataSrc: any[][] = []
    let i = 0;
    this.paginationTableData$.subscribe(data => data.data.filteredData.forEach((obj:any)=> {
      let arr = []
      arr.push(i+1)
      arr.push(obj.startDate, obj.endDate, obj.ulbName, obj.overWritten, obj.status)
      dataSrc.push(arr)
      i = i+1
    }))

    this.dmt.exportToPdf(
      [["Srl No.",  'startDate',
      'endDate',
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
       'startDate': obj.startDate,
       'endDate': obj.endDate,
       'ulbName': obj.ulbName,
       'status':obj.status,
       'overWritten':obj.overWritten.toString(),
      };
      excelJsonData.push(arr)
      i = i+1
    }))
    var wscols = [{wch:6},{wch:15},{wch:15},{wch:25},{wch:10},{wch:10}];

    this.dmt.exportAsExcelFile(excelJsonData, 'division', wscols)
  }


  }

