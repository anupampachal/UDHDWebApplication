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
import { ACDCService } from '../../service/acdc.service';
import { ACDCULBBasedDTO } from '../model/ac-dc.model';

@Component({
  selector: 'app-list-acdc',
  templateUrl: './list-acdc.component.html',
  styleUrls: ['./list-acdc.component.css']
})
export class ListAcdcComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<ACDCULBBasedDTO>> = of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = 'treasuryName';
  sortingDirection = true;
  filterdata = '';
@ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;

  sortOptions: SortOptionModel[] = [
    {
      displayName: 'Name',
      value: 'treasuryName'
    }, {
      displayName: 'ULB Name',
      value: 'ulb'
    }
  ];


  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(data)
      .pipe(map(this.setTableData));
  }
  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(data)
      .pipe(map(this.setTableData));
  }

  showView($event: ACDCULBBasedDTO) {
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
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(private acdcService: ACDCService, private router: Router, private route: ActivatedRoute) { }
  ngOnInit() {
    this.paginationTableData$ = this.acdcService
      .getACDCByPage(
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

  exportTableToPdf() {
    let dataSrc: any[][] = []
    let i = 0;
    this.paginationTableData$.subscribe(data => data.data.filteredData.forEach(obj => {
      let arr = []
      arr.push(i+1)
      arr.push(obj.treasuryCode, obj.budgetCode, obj.billNo, obj.billTVNo,obj.drawn, obj.adjusted,obj.unadjusted)
      dataSrc.push(arr)
      i = i+1
    }))

    this.dmt.exportToPdf(
      [["Srl No.", "Treasury Code", "Budget Code", "Bill No.", "Bill Tv No.", "Drawn", "Adjusted", "Unadjusted"]],
      dataSrc,
      '                                             Ac-Dc'
      )
  }

  setTableData(data: GenericResponseObject<ACDCULBBasedDTO>) {
    const matTableData = new MatTableWithPagination<ACDCULBBasedDTO>();
    matTableData.cols = [
      'treasuryCode',
      'status',
      'budgetCode',
      'billNo',
      'billTVNo',
      'drawn',
      'adjusted',
      'unadjusted',
      'ulbInfo'
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'treasuryCode',
      'status',
      'budgetCode',
      'billNo',
      'billTVNo',
      'drawn',
      'adjusted',
      'unadjusted',
      'ulbInfo',
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
