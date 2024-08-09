import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { ULBDTO } from 'src/app/shared/model/ulb.model';
import { GeoDistrictService } from '../../geo-district/services/geo-district.service';
import { ULBService} from '../services/ulb.service';

@Component({
  selector: 'app-view-ulb',
  templateUrl: './view-ulb.component.html',
  styleUrls: ['./view-ulb.component.css']
})


export class ViewUlbComponent implements OnInit {

  
  deleteError = false;
  ulb$?: Observable<ULBDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private ulbService: ULBService,
    // private geoDistrictService: GeoDistrictService
  ) { }
  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.ulbService.deleteULB(this.id))
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
    this.ulb$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.ulbService.findULBById(param['id'])),
      shareReplay(2)
    );
  }

}
