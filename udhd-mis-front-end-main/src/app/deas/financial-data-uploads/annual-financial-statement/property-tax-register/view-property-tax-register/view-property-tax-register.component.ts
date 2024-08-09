import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay, map } from 'rxjs/operators';
import { FileSaverService } from 'ngx-filesaver';
import { FinYearDateDTO } from '../../fin-year-date-dto'
import { PropertyTaxRegisterService } from '../../services/property-tax-register.service';

@Component({
  selector: 'app-view-property-tax-register',
  templateUrl: './view-property-tax-register.component.html',
  styleUrls: ['./view-property-tax-register.component.css']
})
export class ViewPropertyTaxRegisterComponent implements OnInit {
  deleteError = false;
  propertyTaxData$?: Observable<any>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private service: PropertyTaxRegisterService,
    private _FileSaverService: FileSaverService

  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.propertyTaxData$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findPropertyTaxRegById(param['id'])),
      shareReplay(2)
    );
  }
  downloadFile(fileId: any) {
    this.service.getFileByFileId(fileId).pipe(
      map(res => this._FileSaverService.save(res, 'file.pdf')))
      .subscribe()
  }
}
