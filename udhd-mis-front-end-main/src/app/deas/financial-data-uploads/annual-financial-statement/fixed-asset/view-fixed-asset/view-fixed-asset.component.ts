import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { FileSaverService } from 'ngx-filesaver';
import { FinYearDateDTO } from '../../fin-year-date-dto'
import { FixedAssetService } from '../../services/fixed-asset.service';
@Component({
  selector: 'app-view-fixed-asset',
  templateUrl: './view-fixed-asset.component.html',
  styleUrls: ['./view-fixed-asset.component.css']
})
export class ViewFixedAssetComponent implements OnInit {
  deleteError = false;
  FixedAssetData$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: FixedAssetService,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.FixedAssetData$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findFixedAssetById(param['id'])),
      shareReplay(2)
    );
  }
  downloadFile(fileId: any) {
    this.service.getFileByFileId(fileId).pipe(
      map(res => this._FileSaverService.save(res, 'file.pdf')))
      .subscribe()
  }
}
