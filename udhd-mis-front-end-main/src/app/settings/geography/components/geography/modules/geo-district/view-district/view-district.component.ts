import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { GeoDistrictDTO } from 'src/app/shared/model/geo-district.model';
import { GeoDistrictService } from '../services/geo-district.service';

@Component({
  selector: 'app-view-district',
  templateUrl: './view-district.component.html',
  styleUrls: ['./view-district.component.css']
})
export class ViewDistrictComponent implements OnInit {

  deleteError = false;
  district$?: Observable<GeoDistrictDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private geoDistrictService: GeoDistrictService
  ) { }
  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.geoDistrictService.deleteGeoDistrictById(this.id))
      )
      .subscribe(
        () => this.router.navigate(['../../list'], { relativeTo: this.route }),
        (error) => {
          this.deleteError = true;
          setTimeout(() => (this.deleteError = false), 5000);
        }
      );
  }
  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.district$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.geoDistrictService.findDistrictById(param['id'])),
      shareReplay(2)
    );
  }

}


