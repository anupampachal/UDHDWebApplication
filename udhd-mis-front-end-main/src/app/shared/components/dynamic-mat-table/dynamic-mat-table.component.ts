import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { FormControl } from "@angular/forms";
import { PageEvent } from "@angular/material/paginator";
import { MatTable } from "@angular/material/table";
import { PaginatorModel } from "../../model/paginator.model";
import { MatTableWithPagination } from "../../model/table-with-pagination.model";
import { MatTableDataUpdateService } from "../../service/mat-table-data-update-event.service";
import { MatSort } from '@angular/material/sort';
import { DatePipe } from "@angular/common";
import { jsPDF } from "jspdf"
import 'jspdf-autotable';
import * as fileSaver from 'file-saver';
import * as XLSX from 'xlsx';
const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';
@Component({
  selector: 'app-dynamic-mat-table',
  templateUrl: './dynamic-mat-table.component.html',
  styleUrls: ['./dynamic-mat-table.component.css']
})
export class DynamicMatTable<Type> implements OnChanges {
  @Input() paginationTableData!: MatTableWithPagination<Type>;
  @Output() selectedToView = new EventEmitter<Type>();
  @Output() paginatorEvent = new EventEmitter<PaginatorModel>();
  @ViewChild(MatTable) table?: MatTable<any>;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild('exporter') exportCSV!: any;

  public exportAsExcelFile(json: any[], excelFileName: string, wscols: any): void {

    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    worksheet["!cols"] = wscols;
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, excelFileName);
  }
  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], { type: EXCEL_TYPE });
    fileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  }

  exportToCSV(hiddenCols: number[]) {
    this.exportCSV.hiddenColumns = hiddenCols
    this.exportCSV.exportTable('csv')
  }

  exportToPdf(head: string[][], data: any[][], tableText: string) {
    var doc = new jsPDF();
    doc.setFontSize(18);
    doc.text(tableText, 11, 8);
    doc.setFontSize(11);
    doc.setTextColor(100);
    (doc as any).autoTable({
      head: head,
      body: data,
      theme: 'grid',
    })
    // doc.output('dataurlnewwindow')   //To open in new browser tab
    const fileName = new Date().getTime() + '.pdf'
    doc.save(fileName)
  }
  formControl = new FormControl();
  ngAfterViewInit() {
    this.paginationTableData.data.sort = this.sort;
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.matTableDataUpdateService.tableDataChangeEvent.subscribe(() =>
      this.table?.renderRows()
    );
  }
  constructor(private matTableDataUpdateService: MatTableDataUpdateService, private datePipe: DatePipe) { }

  getServerData(event: PageEvent) {
    const pageModel = new PaginatorModel();
    pageModel.currentPage = event.pageIndex;
    pageModel.pageSize = event.pageSize;
    this.paginatorEvent.emit(pageModel);
  }
  onClick(event: Type) {
    this.selectedToView.emit(event);
  }

  applyFilter(event: Event) {

  }

  getInstanceType(data: any) {
    if (typeof data === 'boolean') return true;
    return false;
  }
  transformData(data: any) {
    if (typeof data === 'boolean') {
      if (data === true)
        return '<mat-icon>done</mat-icon>';
      else if (data === false) return '<mat-icon>cancel</mat-icon>';
    }
    if (typeof data === 'string' && data.length < 25)
      return data;

    else if (typeof data === 'string' && data.length >= 25) {
      return data.substring(0, 30).concat('...');
    }


    return data;
  }
}
