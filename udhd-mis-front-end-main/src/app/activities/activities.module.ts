import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActivitiesRoutingModule } from './activities-routing.module';
import { TodoListComponent } from './todo-list/todo-list.component';
import { HolidayCalenderComponent } from './holiday-calender/holiday-calender.component';
import { EventsAndMeetingsComponent } from './events-and-meetings/events-and-meetings.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { ActivitiesComponent } from './activities.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ViewHolidayCalenderComponent } from './holiday-calender/view-holiday-calender/view-holiday-calender.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CreateEventsAndMeetingsComponent } from './events-and-meetings/create-events-and-meetings/create-events-and-meetings.component';
import { EditEventsAndMeetingsComponent } from './events-and-meetings/edit-events-and-meetings/edit-events-and-meetings.component';
import { ViewEventsAndMeetingsComponent } from './events-and-meetings/view-events-and-meetings/view-events-and-meetings.component';
import { ListEventsAndMeetingComponent } from './events-and-meetings/list-events-and-meeting/list-events-and-meeting.component';
import { AngularEditorModule } from '@kolkov/angular-editor';


@NgModule({
  declarations: [
    TodoListComponent,
    HolidayCalenderComponent,
    EventsAndMeetingsComponent,
    NotificationsComponent,
    ActivitiesComponent,
    ViewHolidayCalenderComponent,
    CreateEventsAndMeetingsComponent,
    EditEventsAndMeetingsComponent,
    ViewEventsAndMeetingsComponent,
    ListEventsAndMeetingComponent
  ],
  imports: [
    CommonModule,
    ActivitiesRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    AngularEditorModule
  ]
})
export class ActivitiesModule { }
