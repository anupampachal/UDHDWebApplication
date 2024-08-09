import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatExpansionPanel } from '@angular/material/expansion';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuditCriteriaDTO } from 'src/app/audit/ag-ir/models/agir-criteria.model';
import { AuditComplianceDTO } from 'src/app/audit/ag-ir/models/agircompliances.model';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { Page } from 'src/app/shared/model/page.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { AGIRAuditComplianceService } from '../compliances/compliance.service';
import { AGIRAuditCriteriaService } from '../criteria.service';
import { AGIRAuditCommentService } from '../comments/comments.service';
import { AuditCommentDTO } from 'src/app/audit/ag-ir/models/agircomment.model';

@Component({
  selector: 'app-list-agir-audit-criteria',
  templateUrl: './list-agir-audit-criteria.component.html',
  styleUrls: ['./list-agir-audit-criteria.component.css']
})
export class ListAgirAuditCriteriaComponent implements OnInit {
  @Input() parent!: string;
  @Input() id!: number;
  @Input() status!: string;
  @Output() criteriaId = new EventEmitter<number>();
  @Output() sendToEdit = new EventEmitter<number>();
  panelOpenState = false;
  openedAccordion: Subject<number> = new Subject();
  complianceView = 'list';
  commentView = 'list';
  complianceList?: MatTableWithPagination<AuditComplianceDTO>;
  commentList?: MatTableWithPagination<AuditCommentDTO>;
  complianceId?: number;
  commentId!: number;
  random!: number;
  @Input() currentCriteriaView = 'list';
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
  onCurrentCriteriaViewChange(event: AuditCriteriaDTO) {
    this.currentCriteriaView = 'list';
  }

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AuditCriteriaDTO) {
    // this.router.navigate(['../view/', $event.id], {
    // relativeTo: this.route,
    //});
    this.criteriaId.emit($event.id);
  }

  setTableDataPage(data: GenericResponseObject<AuditComplianceDTO>) {
    const matTableData = new MatTableWithPagination<AuditComplianceDTO>();
    matTableData.cols = [
      'comment',
      'status',
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'comment',
      'status',
      'actions'
    ];
    matTableData.pageSizeOptions = [10, 20, 50, 100];
    matTableData.data = new MatTableDataSource(data.currentRecords.content);
    matTableData.dataTotLength = data.totalRecords;
    matTableData.pageSize = data.itemsPerPage;
    matTableData.currentPage = data.currentPage;
    this.complianceList = matTableData;
    return matTableData;
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
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(
    private router: Router,
    private agirAuditCriteriaService: AGIRAuditCriteriaService,
    private route: ActivatedRoute,
    private agirAuditComplianceService: AGIRAuditComplianceService,
    private agirAuditCommentService: AGIRAuditCommentService
  ) { }

  ngOnInit() {
    /*this.agirAuditComplianceService
      .getAGIRAuditComplianceByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'comment',
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        )).pipe(map(res => this.setTableDataPage(res))).subscribe();*/
    this.filterdata = this.id;
    this.paginationTableData$ = this.agirAuditCriteriaService
      .getAGIRAuditCriteriaByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          this.currentlySelectedVal,
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        ))
      .pipe(map(this.setTableData));

      /*this.agirAuditCommentService.getAGIRAuditCommentByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'comment',
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        )).pipe(map(res => this.setCommentsTableData(res))).subscribe(
          ()=>{
            this.commentView = 'list';
          }
        );
        */
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
  changeToList(criteriaIdL: number) {

    this.agirAuditComplianceService
      .getAGIRAuditComplianceByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'comment',
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        )).pipe(map(res => this.setTableDataPage(res))).subscribe(
          ()=>{
            this.complianceView = 'list';
          }
        );
    
    
    //this.random = Math.random() * 1000;
    // this.parent=parent;
     //this.openedAccordion.next(criteriaIdL)
  }
  editCompliances(id: number) {
    this.complianceView = 'edit';
    this.complianceId = id;
  }
  editCriteriaClicked(id: number) {
    this.complianceView = 'edit';
    this.currentCriteriaView = 'edit';
    this.complianceId = id;
    this.sendToEdit.emit(id);
  }
  changeToCreateCompliance($event: any) {
    this.complianceView = 'create';
  }

  changeToCreateComments($event: any) {
    this.commentView = 'create';
  }
  changetoViewComment(data: number) {
    this.commentView = 'view';
    this.commentId = data;
  }

  editComment(id: number) {
    this.commentView = 'edit';
    this.commentId = id;
  }

  listComment($event: any) {
    this.agirAuditCommentService.getAGIRAuditCommentByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'comment',
          this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
          this.filterdata
        )).pipe(map(res => this.setCommentsTableData(res))).subscribe(
          ()=>{
            this.commentView = 'list';
          }
        );
    //this.commentView = 'list';
  }

  setCommentsTableData(data: GenericResponseObject<AuditCommentDTO>) {
    const matTableData = new MatTableWithPagination<AuditCommentDTO>();
    matTableData.cols = [
      'comment',
    ];
    matTableData.colsWithAction = [
      'srlNo',
      'comment',
      'actions'
    ];
    matTableData.pageSizeOptions = [10, 20, 50, 100];
    matTableData.data = new MatTableDataSource(data.currentRecords.content);
    matTableData.dataTotLength = data.totalRecords;
    matTableData.pageSize = data.itemsPerPage;
    matTableData.currentPage = data.currentPage;
    this.commentList=matTableData;
    return matTableData;
  }

}
