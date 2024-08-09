import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HistoricalDataUploadRoutingModule } from './historical-data-upload-routing.module';
import { HistoricalDataUploadComponent } from './historical-data-upload.component';
import { RouterModule } from '@angular/router';
import { CreateHistoricalDataUploadComponent } from './create-historical-data-upload/create-historical-data-upload.component';
import { EditHistoricalDataUploadComponent } from './edit-historical-data-upload/edit-historical-data-upload.component';
import { ListHistoricalDataUploadComponent } from './list-historical-data-upload/list-historical-data-upload.component';
import { ViewHistoricalDataUploadComponent } from './view-historical-data-upload/view-historical-data-upload.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { SharedFiltersModule } from '../../sharedFilters/shared-filter.module';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';


@NgModule({
  declarations: [
    HistoricalDataUploadComponent,
    CreateHistoricalDataUploadComponent,
    EditHistoricalDataUploadComponent,
    ListHistoricalDataUploadComponent,
    ViewHistoricalDataUploadComponent,

  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
    FormsModule,ReactiveFormsModule,
    HistoricalDataUploadRoutingModule,
    MatRadioModule,
    MatNativeDateModule,
    MatDatepickerModule,
    SharedFiltersModule,
    MatSlideToggleModule
  ]
})
export class HistoricalDataUploadModule { }
