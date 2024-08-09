import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { ACDCService } from '../../service/acdc.service';
import { ACDCULBBasedDTO } from '../model/ac-dc.model';

@Component({
  selector: 'app-view-acdc',
  templateUrl: './view-acdc.component.html',
  styleUrls: ['./view-acdc.component.css']
})
export class ViewAcdcComponent implements OnInit {

  deleteError = false;
  acdc$?: Observable<ACDCULBBasedDTO>;
  routeArray: ActivatedRoute[] = [];
  id?: number;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private acdcService: ACDCService
  ) { }
  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.acdcService.deleteACDCById(this.id))
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
    this.acdc$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.acdcService.findACDCById(param['id'])),
      shareReplay(2)
    );
  }


}
