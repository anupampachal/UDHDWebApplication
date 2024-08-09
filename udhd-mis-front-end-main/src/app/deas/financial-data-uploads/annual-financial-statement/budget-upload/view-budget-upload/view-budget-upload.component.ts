import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { FileSaverService } from 'ngx-filesaver';
import { BudgetUploadService } from '../../services/budget-upload.service';
import { FinYearDateDTO } from '../../fin-year-date-dto'
import { BudgetUploadDTO } from '../../models/budget-upload-dto';

@Component({
  selector: 'app-view-budget-upload',
  templateUrl: './view-budget-upload.component.html',
  styleUrls: ['./view-budget-upload.component.css']
})
export class ViewBudgetUploadComponent implements OnInit {
  deleteError = false;
    budgetData$?: Observable<any>;
    routeArray: ActivatedRoute[] = [];
    id?: number;
    constructor(
      private route: ActivatedRoute,
      private router: Router,
      public dialog: MatDialog,
      private service: BudgetUploadService,
      private _FileSaverService: FileSaverService

    ) { }

    ngOnInit(): void {
      this.routeArray = this.route.pathFromRoot;
      this.budgetData$ = this.route.params.pipe(
        filter((params) => params && params['id']),
        tap((params) => (this.id = params['id'])),
        switchMap((param) => this.service.findBudgetById(param['id'])),
        shareReplay(2)
      );
    }
    downloadFile(fileId: any) {
      this.service.getFileByFileId(fileId).pipe(
        map(res => this._FileSaverService.save(res, 'file.pdf')))
        .subscribe()
    }
  }
