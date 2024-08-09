import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
import { ConfirmDialogBoxComponent } from '../.././../shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { ActivitiesService } from '../../activities.service';
import { HolidayDTO } from '../holiday-dto';

@Component({
  selector: 'app-view-holiday-calender',
  templateUrl: './view-holiday-calender.component.html',
  styleUrls: ['./view-holiday-calender.component.css']
})
export class ViewHolidayCalenderComponent implements OnInit {
    holiday$?: Observable<HolidayDTO>;
    routeArray: ActivatedRoute[] = [];
    id?: number;
    deleteError = false;

    constructor(
      private route: ActivatedRoute,
      private router: Router,
      private service: ActivitiesService,
    public dialog: MatDialog,

    ) { }

      ngOnInit(): void {
        this.routeArray = this.route.pathFromRoot;
        this.holiday$ = this.route.params.pipe(
          filter((params) => params && params['id']),
          tap((params) => (this.id = params['id'])),
          switchMap((param) => this.service.findHolidayById(param['id'])),
          shareReplay(2)
        );
    }
    confirmDelete() {
      const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
      dialogRef
        .afterClosed()
        .pipe(
          filter((res) => res === true && !!this.id),
          switchMap(() => this.service.deleteHolidayById(this.id))
        )
        .subscribe(
          () => this.router.navigate(['../../list'], { relativeTo: this.route }),
          (error) => {
            this.deleteError = true;
            setTimeout(() => (this.deleteError = false), 5000);
          }
        );
    }

  }
