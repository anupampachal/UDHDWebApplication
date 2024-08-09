import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { FileSaverService } from 'ngx-filesaver';
import { BudgetUploadService } from '../../services/budget-upload.service';
import { FinYearDateDTO } from '../../fin-year-date-dto'
import { TrialBalanceService } from '../../services/trial-balance.service';

@Component({
  selector: 'app-view-trial-balance',
  templateUrl: './view-trial-balance.component.html',
  styleUrls: ['./view-trial-balance.component.css']
})
export class ViewTrialBalanceComponent implements OnInit {
  deleteError = false;
  trialBalanceData$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: TrialBalanceService,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.trialBalanceData$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findTrialBalanceById(param['id'])),
      shareReplay(2)
    );
    this.trialBalanceData$.subscribe()
  }
  downloadFile(fileId: any) {
    this.service.getFileByFileId(fileId).pipe(
      map(res => this._FileSaverService.save(res, 'file.xlsx')))
      .subscribe()
  }
  downloadSummaryFile(fileId: any) {
    this.service.getSummaryFile(fileId).pipe(
      map(res => this._FileSaverService.save(res, new Date().getTime() +'.xlsx')))
      .subscribe()
  }
}
