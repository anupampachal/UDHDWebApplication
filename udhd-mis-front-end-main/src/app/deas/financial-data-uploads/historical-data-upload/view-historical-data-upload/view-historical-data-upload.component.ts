import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { HistoricalDataUploadService } from 'src/app/deas/services/historical-data-upload.service';
import { FileSaverService } from 'ngx-filesaver';

@Component({
  selector: 'app-view-historical-data-upload',
  templateUrl: './view-historical-data-upload.component.html',
  styleUrls: ['./view-historical-data-upload.component.css']
})
export class ViewHistoricalDataUploadComponent implements OnInit {

  deleteError = false;
  historicalData$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: HistoricalDataUploadService,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.historicalData$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findHistoricalDataById(param['id'])),
      shareReplay(2)
    );
  }
  downloadFile(fileId: any) {
    this.service.getFileByFileId(fileId).pipe(
      map(res => this._FileSaverService.save(res, 'file.pdf')))
      .subscribe()
  }
}
