import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { Observable, of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map, catchError } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { InternalAuditService } from '../../internal-audit-service.service';
import { AuditObsDTO } from '../../models/auditObsDTO';
import { Step1ReportService } from '../../services/step-1-report.service';
import { Step4Service } from '../../services/step-4.service';

@Component({
  selector: 'app-view-step4-outstanding-paras-review',
  templateUrl: './view-step4-outstanding-paras-review.component.html',
  styleUrls: ['./view-step4-outstanding-paras-review.component.css']
})
export class ViewStep4OutstandingParasReviewComponent implements OnInit {

  auditObservationList: AuditObsDTO[] = []
  auditParaList: AuditObsDTO[] = []
  deleteError = false;
  step4ObsReport$?: Observable<any>;
  step4ParaReport$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: Step4Service,
    private service1: Step1ReportService,
    private _FileSaverService: FileSaverService

  ) { }


  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.step4ObsReport$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.getAuditObsList(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );

    this.step4ParaReport$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.getAuditParaList(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );
  }
  downloadFile(id: number) {
    this.service.getAuditParaFile(id).pipe(
    map(res => this._FileSaverService.save(res, 'auditParafile.pdf'))
    ).subscribe((res) => {
      this.service1.show("Audit Para File Successfully Downloaded", "success")

    })
  }
}
