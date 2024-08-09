import { AGIRAuditDTO } from '../../../models/agir-audit.model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GenericResponseObject } from 'src/app/shared/model/generic-response-object-pagination.model';
import { PaginationModel } from 'src/app/shared/model/pagination-request.model';
import { PaginatorModel } from 'src/app/shared/model/paginator.model';
import { SortOptionModel } from 'src/app/shared/model/sort-option.model';
import { MatTableWithPagination } from 'src/app/shared/model/table-with-pagination.model';
import { AGIRAuditService } from '../agir-audit.service';
import { DynamicMatTable } from 'src/app/shared/components/dynamic-mat-table/dynamic-mat-table.component';

@Component({
  selector: 'app-list-agir-audit',
  templateUrl: './list-agir-audit.component.html',
  styleUrls: ['./list-agir-audit.component.css'],
})
export class ListAgirAuditComponent implements OnInit {
  paginationTableData$: Observable<MatTableWithPagination<AGIRAuditDTO>> = of();
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
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  filteredDataEvent(data: PaginationModel) {
    this.filterdata = data && data.queryString ? data.queryString : '';
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(data)
      .pipe(map(this.setTableData));
  }

  getSortedByEvent(data: PaginationModel) {
    this.currentlySelectedVal = data.sortedBy ? data.sortedBy : '';
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  sortingDirectionChangeEvent(data: PaginationModel) {
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(data)
      .pipe(map(this.setTableData));
  }
  showView($event: AGIRAuditDTO) {
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
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(page)
      .pipe(map(this.setTableData));
  }

  constructor(
    private router: Router,
    private agirAuditService: AGIRAuditService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.paginationTableData$ = this.agirAuditService
      .getAGIRAuditByPage(
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

  setTableData(data: GenericResponseObject<AGIRAuditDTO>) {
    const matTableData = new MatTableWithPagination<AGIRAuditDTO>();
    matTableData.cols = ['title', 'ulbName','startDate', 'endDate', 'description',  'auditStatus'];
    matTableData.colsWithAction = ['srlNo', 'ulbName','title', 'startDate', 'endDate', 'description',  'auditStatus', 'actions',];
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
      '                                             AGIR Audit List'
    )
  }
  exportTableToSpreadsheet() {
    this.dmt.exportToCSV([7])
  }

  assignedToMeAGIRAudit(){
   this.router.navigate(['../list-assigned-to-me'],{relativeTo:this.route});
  }

  unassignedAGIRAudit(){
    this.router.navigate(['../unassigned-list'], {
      relativeTo: this.route,
    });
  }

}
