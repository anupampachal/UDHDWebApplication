import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, shareReplay, switchMap, tap } from 'rxjs/operators';
//import { ConfirmDialogBoxComponent } from 'src/app/shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { ConfirmDialogBoxComponent } from '../../../shared/components/confirm-dialog-box/confirm-dialog-box.component';
import { ActivitiesService } from '../../activities.service';
import { EventsNMeetingsDTO } from '../events-nmeetings-dto';

@Component({
  selector: 'app-view-events-and-meetings',
  templateUrl: './view-events-and-meetings.component.html',
  styleUrls: ['./view-events-and-meetings.component.css']
})
export class ViewEventsAndMeetingsComponent implements OnInit {
  eventsNmeetings$?: Observable<EventsNMeetingsDTO>;
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
    this.eventsNmeetings$ = this.route.params.pipe(
      filter((params) => params && params['id']),
      tap((params) => (this.id = params['id'])),
      switchMap((param) => this.service.findMeetingsById(param['id'])),
      shareReplay(2)
    );
  }

  confirmDelete() {
    const dialogRef = this.dialog.open(ConfirmDialogBoxComponent);
    dialogRef
      .afterClosed()
      .pipe(
        filter((res) => res === true && !!this.id),
        switchMap(() => this.service.deleteMeetingById(this.id))
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
