import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, switchMap, tap, shareReplay } from 'rxjs/operators';
import { ActivitiesService } from '../../activities.service';
import { EventsNMeetingsDTO } from '../events-nmeetings-dto';

@Component({
  selector: 'app-edit-events-and-meetings',
  templateUrl: './edit-events-and-meetings.component.html',
  styleUrls: ['./edit-events-and-meetings.component.css']
})
export class EditEventsAndMeetingsComponent implements OnInit {
  members$?: Observable<EventsNMeetingsDTO>;
  updateEventsNMeetingsForm!: FormGroup;
  routeArray: ActivatedRoute[] = [];
  active: boolean = false;
  data?: EventsNMeetingsDTO;
  allAttendies = []
  showFormError?: ValidationErrors;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: ActivitiesService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.route.params
      .pipe(
        filter((params) => params && params['id']),
        switchMap((param) => this.service.findMeetingsById(param['id'])),
        tap((dt) => this.setData(dt)),
        shareReplay(2)
      )
      .subscribe((res) => this.setData(res));

    this.updateEventsNMeetingsForm = this.fb.group({
      meetingHeader: new FormControl(null, [Validators.required]),
      meetingLocation: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      startDate: new FormControl(null, [Validators.required]),
      endDate: new FormControl(null, [Validators.required]),
      startTime: new FormControl(null, [Validators.required]),
      endTime: new FormControl(null, [Validators.required]),
      requiredAttendies: new FormControl(null, [Validators.required]),
      optionalAttendies: new FormControl(null, [Validators.required]),
    });
  }
  setData(dt: EventsNMeetingsDTO) {
    this.data = dt;
    this.updateEventsNMeetingsForm.setValue({
      id: dt.id,
      meetingHeader: dt.meetingHeader,
      description: dt.description,
      meetingLocation: dt.meetingLocation,
      startDate: dt.startDate,
      endDate: dt.endDate,
      startTime: dt.startTime,
      endTime: dt.endTime,
      requiredAttendies: dt.requiredAttendies,
      optionalAttendies: dt.optionalAttendies,
    });
  }


  onSubmit() {
    if (this.updateEventsNMeetingsForm.valid && !!this.data && !!this.data.id) {
      this.service.createEventAndMeeting(this.updateEventsNMeetingsForm.getRawValue()).subscribe((res) =>
        this.router.navigate(['../../view/', res.id], {
          relativeTo: this.route,
        })
      );
    }

  }

}
