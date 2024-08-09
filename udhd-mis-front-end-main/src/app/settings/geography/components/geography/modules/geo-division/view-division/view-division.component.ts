import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { GeoDivisionDTO } from 'src/app/shared/model/geo-division.model';
import { GeoDivisionService } from '../services/geo-division.service';

@Component({
  selector: 'app-view-division',
  templateUrl: './view-division.component.html',
  styleUrls: ['./view-division.component.css']
})
export class ViewDivisionComponent implements OnInit {

  
  deleteError = false;
  division$?: Observable<GeoDivisionDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private geoDivisionService: GeoDivisionService
  ) { }
  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.geoDivisionService.deletePhysicalStorageById(this.id))
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
    this.division$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.geoDivisionService.findDivisionById(param['id'])),
      shareReplay(2)
    );
  }

}
