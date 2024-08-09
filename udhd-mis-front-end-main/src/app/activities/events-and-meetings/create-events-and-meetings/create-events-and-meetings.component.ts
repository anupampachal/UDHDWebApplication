import { Component, OnInit } from '@angular/core';
import { FormGroup, ValidationErrors, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MyErrorStateMatcher } from 'src/app/shared/input-error-state-matcher';
import { ActivitiesService } from '../../activities.service';

@Component({
  selector: 'app-create-events-and-meetings',
  templateUrl: './create-events-and-meetings.component.html',
  styleUrls: ['./create-events-and-meetings.component.css']
})
export class CreateEventsAndMeetingsComponent implements OnInit {
  createMeetingForm!: FormGroup;
  showFormError?: ValidationErrors;
  active = false
  routeArray: ActivatedRoute[] = [];
  allAttendies = []
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private service: ActivitiesService
  ) { }

  ngOnInit(): void {
    this.routeArray = this.route.pathFromRoot;
    this.createMeetingForm = this.fb.group({
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

  matcher = new MyErrorStateMatcher();
  onSubmit() {
    if (this.createMeetingForm.valid) {
      this.service
        .createEventAndMeeting(this.createMeetingForm.value)
        .subscribe((res) =>
          this.router.navigate(['../view/', res.id], {
            relativeTo: this.route,
          })
        );
    } else if (!!this.createMeetingForm.errors) {
      this.showFormError = this.createMeetingForm.errors;
    }
  }


}
