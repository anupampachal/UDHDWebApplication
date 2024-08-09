import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { filter, map, switchMap } from 'rxjs/operators';
import { AuditCommentDTO } from 'src/app/audit/finance/models/financecomment.model';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { FinanceAuditCommentService } from '../comments.service';

@Component({
  selector: 'app-list-finance-audit-comments',
  templateUrl: './list-finance-audit-comments.component.html',
  styleUrls: ['./list-finance-audit-comments.component.css']
})
export class ListFinanceAuditCommentsComponent implements OnInit {
  @Input() openedAccordion!: Subject<number>;
  @Input() criteriaId!: number;

  constructor(private financeAuditCommentsService: FinanceAuditCommentService,
    private router: Router,
    private route: ActivatedRoute) { }


  ngOnInit(): void {
    this.paginationTableData$ = this.openedAccordion.pipe(
      filter(data => data === this.criteriaId),
      switchMap(() =>
        this.financeAuditCommentsService
          .getFinanceAuditCommentByPage(
            this.getPageModel(
              'ALL',
              0,
              10,
              this.currentlySelectedVal,
              this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
              this.filterdata
            ))
      ),
      map(this.setTableData)
    );
    this.routeArray = this.route.pathFromRoot;
  }


  paginationTableData$: Observable<MatTableWithPagination<AuditCommentDTO>> =
    of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = 'comment';
  sortingDirection = true;
  filterdata = '';
  sortOptions: SortOptionModel[] = [
    {
      displayName: 'Comment',
      value: 'comment'
    }
  ];

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.financeAuditCommentsService
      .getFinanceAuditCommentByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.financeAuditCommentsService
      .getFinanceAuditCommentByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.financeAuditCommentsService
      .getFinanceAuditCommentByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.financeAuditCommentsService
      .getFinanceAuditCommentByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AuditCommentDTO) {
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
    this.paginationTableData$ = this.financeAuditCommentsService
      .getFinanceAuditCommentByPage(page)
      .pipe(map(this.setTableData));
  }




  setTableData(data: GenericResponseObject<AuditCommentDTO>) {
    const matTableData = new MatTableWithPagination<AuditCommentDTO>();
    matTableData.cols = [
      'comment',
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'comment',
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
    page.id=this.criteriaId;
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
