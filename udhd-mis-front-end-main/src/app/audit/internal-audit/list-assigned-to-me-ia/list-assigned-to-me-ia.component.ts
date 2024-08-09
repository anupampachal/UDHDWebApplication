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
import { AuditReportViewResponseDTO } from '../models/audit-report-view-response.model';
import { Step1ReportService } from '../services/step-1-report.service';

@Component({
  selector: 'app-list-assigned-to-me-ia',
  templateUrl: './list-assigned-to-me-ia.component.html',
  styleUrls: ['./list-assigned-to-me-ia.component.css']
})
export class ListAssignedToMeIaComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<AuditReportViewResponseDTO>> = of();
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
  @ViewChild(DynamicMatTable) dmt!: DynamicMatTable<PaginationModel>;


  clearDataEvent(data: PaginationModel) {
    this.filterdata = '';
    this.paginationTableData$ = this.service.getStep1ReportByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.service.getStep1ReportByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.service.getStep1ReportByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.service.getStep1ReportByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AuditReportViewResponseDTO) {
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
    this.paginationTableData$ = this.service.getStep1ReportByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(
    private router: Router,
    private service: Step1ReportService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit(): void {
    this.paginationTableData$ = this.service
      .getStep1ReportAssignedToMeByPage(
        this.getPageModel(
          'ALL',
          0,
          10,
          'updatedOn',
          'DESCENDING',
          this.filterdata
        )).pipe(map(this.setTableData));
      this.paginationTableData$.subscribe()
    this.routeArray = this.route.pathFromRoot;
  }

  setTableData(data: GenericResponseObject<AuditReportViewResponseDTO>): any {
    const matTableData = new MatTableWithPagination<AuditReportViewResponseDTO>();
    matTableData.cols = ['title', 'startDate', 'endDate', 'ulbName', 'auditStatus'];
    matTableData.colsWithAction = ['srlNo', 'title', 'startDate', 'endDate', 'ulbName', 'auditStatus', 'actions',];
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
      arr.push(i + 1)
      arr.push(obj.title, obj.description, obj.startDate, obj.endDate, obj.ulbName)
      dataSrc.push(arr)
      i = i + 1
    }))

    this.dmt.exportToPdf(
      [["Srl No.", "Title", "Description", "Start Date", "End Date", "Ulb Name"]],
      dataSrc,
      '                                             '
    )
  }
  exportTableToSpreadsheet() {
    this.dmt.exportToCSV([6])
  }
  unassignedIAAudit(){
    this.router.navigate(['../unassigned-list'], {
      relativeTo: this.route,
    });
  } 
}
