import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssignedTicketsComponent } from './assigned-tickets/assigned-tickets.component';
import { CreateAssignedTicketsComponent } from './assigned-tickets/create-assigned-tickets/create-assigned-tickets.component';
import { EditAssignedTicketsComponent } from './assigned-tickets/edit-assigned-tickets/edit-assigned-tickets.component';
import { ListAssignedTicketsComponent } from './assigned-tickets/list-assigned-tickets/list-assigned-tickets.component';
import { ViewAssignedTicketsComponent } from './assigned-tickets/view-assigned-tickets/view-assigned-tickets.component';
import { CreateTicketComponent } from './my-tickets/create-ticket/create-ticket.component';
import { EditTicketComponent } from './my-tickets/edit-ticket/edit-ticket.component';
import { ListTicketComponent } from './my-tickets/list-ticket/list-ticket.component';
import { MyTicketsComponent } from './my-tickets/my-tickets.component';
import { ViewTicketComponent } from './my-tickets/view-ticket/view-ticket.component';
import { SupportComponent } from './support.component';
import { UnassignedTicketsComponent } from './unassigned-tickets/unassigned-tickets.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'support',
  },
  {
    path: 'support',
    component: SupportComponent,
    data: {
      breadcrumb: 'support'
    },
    children: [
      {
        path: 'assigned-tickets',
        component: AssignedTicketsComponent,
        data: {
          breadcrumb: 'Assigned Tickets'
        },
        children: [
          {
            path: '',
            redirectTo: 'list',
            data: {
              breadcrumb: 'list'
            },
          },{
            path: 'list',
            component: ListAssignedTicketsComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewAssignedTicketsComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },{
        path: 'unassigned-tickets',
        component: UnassignedTicketsComponent,
        data: {
          breadcrumb: 'Unassigned Tickets'
        },
      },{
          path: 'myTickets',
          component: MyTicketsComponent,
          data: {
            breadcrumb: 'My Tickets'
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
              component: CreateTicketComponent,
              data: {
                breadcrumb: 'create'
              },
            },
            {
              path: 'edit/:id',
              component: EditTicketComponent,
              data: {
                breadcrumb: 'edit'
              },
            },
            {
              path: 'list',
              component: ListTicketComponent,
              data: {
                breadcrumb: 'list'
              },
            },
            {
              path: 'view/:id',
              component: ViewTicketComponent,
              data: {
                breadcrumb: 'view'
              },
            }
          ]
      },
    ]
  }
]
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SupportRoutingModule { }
