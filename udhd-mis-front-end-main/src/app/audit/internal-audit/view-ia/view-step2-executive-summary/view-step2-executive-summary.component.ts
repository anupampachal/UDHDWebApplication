import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FileSaverService } from 'ngx-filesaver';
import { Observable, of } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map, catchError } from 'rxjs/operators';
import { SworDTO } from '../../models/sworDTO';
import { Step1ReportService } from '../../services/step-1-report.service';
import { Step2Service } from '../../services/step-2.service';

@Component({
  selector: 'app-view-step2-executive-summary',
  templateUrl: './view-step2-executive-summary.component.html',
  styleUrls: ['./view-step2-executive-summary.component.css']
})
export class ViewStep2ExecutiveSummaryComponent implements OnInit {

  commentFromMgt: string = ""
  deleteError = false;
  step2Data$?: Observable<any>;
  comment$?: Observable<any>
  ack$?: Observable<any>
  opinion$?: Observable<any>
  strengths: SworDTO[] = []
  weaknesses:SworDTO[] = []
  recommendations:SworDTO[] = []
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private service: Step2Service,
    private service1: Step1ReportService,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.step2Data$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.getExSummaryByIaId(param['id'])),
      catchError(() => {
        return of()
      }),
      shareReplay(2)
    );
   this.comment$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap(params => this.service.getCOAKByType({type: "COMMENT",iaId: params['id'] as number})
    ))

    this.opinion$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap(params => this.service.getCOAKByType({type: "OPINION",iaId: params['id'] as number})
    ))
    this.ack$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      switchMap(params => this.service.getCOAKByType({type: "ACKNOWLEDGEMENT",iaId: params['id'] as number})
    ))

    this.service.getSworList(this.service1.step1Report.id, "STRENGTH")
    .subscribe((res: any) => {
      this.strengths = res
    },
    error => {}
    )
    this.service.getSworList(this.service1.step1Report.id, "WEAKNESS")
    .subscribe((res: any) => {
      this.weaknesses = res
    },
    error => {}
    )
    this.service.getSworList(this.service1.step1Report.id, "RECOMMENDATION")
    .subscribe((res: any) => {
      this.recommendations = res
    },
    error => {}
    )
}

downloadFile(st2Id: string) {
  this.service.downloadFile(st2Id).pipe(
    map(res => this._FileSaverService.save(res, 'file.pdf'))
  )
  .subscribe(res => {
    this.service1.show("File downloaded successfully", "success", 6000)
  })
}
}
