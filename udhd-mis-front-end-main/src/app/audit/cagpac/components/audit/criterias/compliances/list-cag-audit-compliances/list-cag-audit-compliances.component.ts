import { AfterViewInit, ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { filter, map, share, shareReplay, switchMap, tap } from 'rxjs/operators';
import { AuditComplianceDTO } from 'src/app/audit/cagpac/models/cag-compliances.model';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { CAGAuditComplianceService } from '../compliance.service';

@Component({
  selector: 'app-list-cag-audit-compliances',
  templateUrl: './list-cag-audit-compliances.component.html',
  styleUrls: ['./list-cag-audit-compliances.component.css']
})
export class ListCagAuditCompliancesComponent implements OnChanges, OnInit {
  @Input()auditStatus!:string;
  @Input() openedAccordion!: Subject<number>;
  @Input() criteriaId!: number;
  @Input() parent!: string;
  @Input() pageDataFromParent!: MatTableWithPagination<AuditComplianceDTO>;
  counter = 1;
  @Output() addCompliancesEvent = new EventEmitter<string>();
  @Output() viewCompliancesEvent = new EventEmitter<number>();
  @Input() random!: number;
  paginationTableData$: Subject<MatTableWithPagination<AuditComplianceDTO>> = new Subject();
  //of();
  paginationTableData!: MatTableWithPagination<AuditComplianceDTO>;
  routeArray: ActivatedRoute[] = [];
  cols!: string[];
  colsWithAction!: string[];
  pageSizeOptions!: number[];
  data = new MatTableDataSource();
  showFilter = false;
  currentlySelectedVal = 'comment';
  sortingDirection = true;
  filterdata = '';
  constructor(private cagAuditComplianceService: CAGAuditComplianceService,
    private router: Router,
    private route: ActivatedRoute, private changeDetector: ChangeDetectorRef) {
    }
  ngOnInit(): void {
    console.log("this.criteriaId",this.criteriaId)
    /*this.paginationTableData$.subscribe(res => {
      this.setTableData(this.pageDataFromParent);
    });*/
    this.openedAccordion.pipe(
      //tap(()=>console.log('inside list compliances')),
      switchMap(()=>this.cagAuditComplianceService.getCAGAuditComplianceByPage(
      this.getPageModel(
        'ALL',
        0,
        10,
        'comment',
        this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
        this.filterdata
      )
    ))).subscribe(res=>this.setTableDataPage(res));/*
    this.cagAuditComplianceService.getCAGAuditComplianceByPage(
      this.getPageModel(
        'ALL',
        0,
        10,
        'comment',
        this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
        this.filterdata
      )
    ).subscribe(res => {
      this.setTableDataPage(res);
    });*/
  }
  ngOnChanges(changes: SimpleChanges): void {
    /*
    this.cagAuditComplianceService.getCAGAuditComplianceByPage(
      this.getPageModel(
        'ALL',
        0,
        10,
        'comment',
        this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
        this.filterdata
      )
    ).subscribe(res => {
      this.setTableDataPage(res);
    });*/
  }



  sortOptions: SortOptionModel[] = [
    {
      displayName: 'Comment',
      value: 'comment',
    }, {
      displayName: 'Status',
      value: 'status'
    }
  ];

  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.cagAuditComplianceService
      .getCAGAuditComplianceByPage(data)
      .pipe(map(this.setTableDataPage)).subscribe();
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.cagAuditComplianceService
      .getCAGAuditComplianceByPage(data)
      .pipe(map(this.setTableDataPage))
      .subscribe();
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.cagAuditComplianceService
      .getCAGAuditComplianceByPage(data)
      .pipe(map(this.setTableDataPage)).subscribe();
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.cagAuditComplianceService
      .getCAGAuditComplianceByPage(data)
      .pipe(map(this.setTableDataPage)).subscribe();
  }
  showView($event: AuditComplianceDTO) {
   
    this.viewCompliancesEvent.emit($event.id);
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
    this.cagAuditComplianceService
      .getCAGAuditComplianceByPage(page)
      .pipe(map(this.setTableDataPage)).subscribe();
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
    this.paginationTableData = matTableData;
    return matTableData;
  }



  setTableData(data: MatTableWithPagination<AuditComplianceDTO>) {
    return data;
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
    // page.sortedBy = sortedBy;
    page.id = this.criteriaId;
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

  add() {
    this.addCompliancesEvent.emit('add');
  }
}
