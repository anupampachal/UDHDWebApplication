import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { FileSaverService } from 'ngx-filesaver';
import { FinancialStatementsService } from 'src/app/deas/services/financial-statements.service';

@Component({
  selector: 'app-view-annual-financial-statement',
  templateUrl: './view-annual-financial-statement.component.html',
  styleUrls: ['./view-annual-financial-statement.component.css']
})
export class ViewAnnualFinancialStatementComponent implements OnInit {

    deleteError = false;
    AFSData$?: Observable<any>;
    routeArray: ActivatedRoute[] = [];
    id?: number;
    constructor(
      private route: ActivatedRoute,
      private router: Router,
      public dialog: MatDialog,
      private service: FinancialStatementsService,
      private _FileSaverService: FileSaverService

    ) { }

    ngOnInit(): void {
      this.routeArray = this.route.pathFromRoot;
      this.AFSData$ = this.route.params.pipe(
        filter((params) => params && params['id']),
        tap((params) => (this.id = params['id'])),
        switchMap((param) => this.service.findAFSDataById(param['id'])),
        shareReplay(2)
      );
    }
    downloadFile(fileId: any) {
      this.service.getFileByFileId(fileId).pipe(
        map(res => this._FileSaverService.save(res, 'file.pdf')))
        .subscribe()
    }
  }
