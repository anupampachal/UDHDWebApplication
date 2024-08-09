import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/finance/models/finance-criteria.model';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { Page } from 'src/app/shared/model/page.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { FinanceAuditCriteriaService } from '../criteria.service';

@Component({
  selector: 'app-list-finance-audit-criteria',
  templateUrl: './list-finance-audit-criteria.component.html',
  styleUrls: ['./list-finance-audit-criteria.component.css']
})
export class ListFinanceAuditCriteriaComponent {
  @Input() parent!: string;
  @Input() id!: number;
  @Output() criteriaId = new EventEmitter<number>();
  @Output() sendToEdit = new EventEmitter<number>();
  panelOpenState = false;
  openedAccordion: Subject<number> = new Subject();
  complianceView = 'list';
  complianceId?: number;

  currentCriteriaView = 'list';
  auditCriteriaDTOs!: Page<AuditCriteriaDTO>;
  paginationTableData$: Observable<MatTableWithPagination<AuditCriteriaDTO>> =
    of();
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = 'title';
  sortingDirection = true;
  filterdata: any = '';
  sortOptions: SortOptionModel[] = [
    {
      displayName: 'Title',
      value: 'title'
    }
  ];

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AuditCriteriaDTO) {
    // this.router.navigate(['../view/', $event.id], {
    // relativeTo: this.route,
    //});
    this.criteriaId.emit($event.id);
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
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(
    private router: Router,
    private financeAuditCriteriaService: FinanceAuditCriteriaService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.filterdata = this.id;
    this.paginationTableData$ = this.financeAuditCriteriaService
      .getFinanceAuditCriteriaByPage(
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

  setTableData(data: GenericResponseObject<AuditCriteriaDTO>) {
    const matTableData = new MatTableWithPagination<AuditCriteriaDTO>();
    this.auditCriteriaDTOs = data.currentRecords;
    matTableData.cols = [
      'title',
      'description',
      'auditPara',
      'status',
      'associatedRisk',
      'amount'
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'title',
      'description',
      'auditPara',
      'status',
      'associatedRisk',
      'amount',
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
    if (this.id) {
      page.id = this.id;
    }

    page.sortedBy = sortedBy;
    page.directionOfSort = directionOfSort;
    // if (queryString != '') {
    // page.queryString = queryString;
    //}
    return page;
  }

  getServerData(event: PageEvent) {
    const pageModel = new PaginatorModel();
    pageModel.currentPage = event.pageIndex;
    pageModel.pageSize = event.pageSize;
    this.changeDataForPagination(pageModel);
  }


  onPanelExpand(id: number) {
    if (id > 0) {
      this.openedAccordion.next(id);
    }
  }
  changeView(data: number) {
    this.complianceView = 'view';
    this.complianceId = data;
  }

  editClicked(id: number) {
    this.currentCriteriaView = 'edit';
    this.sendToEdit.emit(id);
  }
}
