import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SupportRoutingModule } from './support-routing.module';
import { SupportComponent } from './support.component';
import { AssignedTicketsComponent } from './assigned-tickets/assigned-tickets.component';
import { UnassignedTicketsComponent } from './unassigned-tickets/unassigned-tickets.component';
import { MyTicketsComponent } from './my-tickets/my-tickets.component';
import { CreateAssignedTicketsComponent } from './assigned-tickets/create-assigned-tickets/create-assigned-tickets.component';
import { EditAssignedTicketsComponent } from './assigned-tickets/edit-assigned-tickets/edit-assigned-tickets.component';
import { ListAssignedTicketsComponent } from './assigned-tickets/list-assigned-tickets/list-assigned-tickets.component';
import { ViewAssignedTicketsComponent } from './assigned-tickets/view-assigned-tickets/view-assigned-tickets.component';
import { CreateTicketComponent } from './my-tickets/create-ticket/create-ticket.component';
import { EditTicketComponent } from './my-tickets/edit-ticket/edit-ticket.component';
import { ListTicketComponent } from './my-tickets/list-ticket/list-ticket.component';
import { ViewTicketComponent } from './my-tickets/view-ticket/view-ticket.component';
import { SharedModule } from '../shared/shared.module';
import { SupportService } from './services/support.service';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    SupportComponent,
    AssignedTicketsComponent,
    UnassignedTicketsComponent,
    MyTicketsComponent,
    CreateAssignedTicketsComponent,
    EditAssignedTicketsComponent,
    ListAssignedTicketsComponent,
    ViewAssignedTicketsComponent,
    CreateTicketComponent,
    EditTicketComponent,
    ListTicketComponent,
    ViewTicketComponent
  ],
  imports: [
    CommonModule,
    SupportRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    SupportService
  ]
})
export class SupportModule { }
