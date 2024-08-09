import { FinanceAuditDTO } from '../../../models/finance-audit.model';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { FinanceAuditService } from '../finance-audit.service';

@Component({
  selector: 'app-list-finance-audit',
  templateUrl: './list-finance-audit.component.html',
  styleUrls: ['./list-finance-audit.component.css'],
})
export class ListFinanceAuditComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<FinanceAuditDTO>> = of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = '';
  sortingDirection = true;
  filterdata = '';
  sortOptions: SortOptionModel[] = [];

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: FinanceAuditDTO) {
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
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(
    private router: Router,
    private financeAuditService: FinanceAuditService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.paginationTableData$ = this.financeAuditService
      .getFinanceAuditByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          this.currentlySelectedVal,
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        )
      )
      .pipe(map(this.setTableData));
    this.routeArray = this.route.pathFromRoot;
  }

  setTableData(data: GenericResponseObject<FinanceAuditDTO>) {
    const matTableData = new MatTableWithPagination<FinanceAuditDTO>();
    matTableData.cols = ['title', 'startDate', 'endDate', 'description', 'ulbName', 'auditStatus'];
    matTableData.colsWithAction = ['srlNo', 'title', 'startDate', 'endDate', 'description', 'ulbName', 'auditStatus', 'actions',];
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

  showSummary(){
    
    this.router.navigate(['../summary'], {
      relativeTo: this.route,
    });
  }
}
