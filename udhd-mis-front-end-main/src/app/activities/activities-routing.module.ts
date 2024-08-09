import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ActivitiesComponent } from './activities.component';
import { CreateEventsAndMeetingsComponent } from './events-and-meetings/create-events-and-meetings/create-events-and-meetings.component';
import { EditEventsAndMeetingsComponent } from './events-and-meetings/edit-events-and-meetings/edit-events-and-meetings.component';
import { EventsAndMeetingsComponent } from './events-and-meetings/events-and-meetings.component';
import { ListEventsAndMeetingComponent } from './events-and-meetings/list-events-and-meeting/list-events-and-meeting.component';
import { ViewEventsAndMeetingsComponent } from './events-and-meetings/view-events-and-meetings/view-events-and-meetings.component';
import { HolidayCalenderComponent } from './holiday-calender/holiday-calender.component';
import { ViewHolidayCalenderComponent } from './holiday-calender/view-holiday-calender/view-holiday-calender.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { TodoListComponent } from './todo-list/todo-list.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'activities',
  },
  {
    path: 'activities',
    component: ActivitiesComponent,
    data: {
      breadcrumb: 'activities'
    },
    children: [
      {
        path: 'events-and-meetings',
        component: EventsAndMeetingsComponent,
        data: {
          breadcrumb: 'events and meetings'
        },
        children: [
          {
            path: '',
            redirectTo: 'list',
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'create',
            component: CreateEventsAndMeetingsComponent,
            data: {
              breadcrumb: 'create'
            },
          },
          {
            path: 'edit/:id',
            component: EditEventsAndMeetingsComponent,
            data: {
              breadcrumb: 'edit'
            },
          },
          {
            path: 'list',
            component: ListEventsAndMeetingComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewEventsAndMeetingsComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
      {
        path: 'holiday-calender',
        component: HolidayCalenderComponent,
        data: {
          breadcrumb: 'holiday-calender'
        }
      },
      {
        path: 'holiday-calender/view/:id',
        component: ViewHolidayCalenderComponent,
        data: {
          breadcrumb: 'holiday-calender'
        }
      },
      {
        path: 'notifications',
        component: NotificationsComponent,
        data: {
          breadcrumb: 'notifications'
        }
      },
      {
        path: 'todo-list',
        component: TodoListComponent,
        data: {
          breadcrumb: 'todo-list'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActivitiesRoutingModule { }
