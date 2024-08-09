import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PeriodicUploadsRoutingModule } from './periodic-uploads-routing.module';
import { PeriodicUploadsComponent } from './periodic-uploads.component';
import { TrialBalanceComponent } from './trial-balance/trial-balance.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatTabsModule } from '@angular/material/tabs';
import { ListTrialBalanceComponent } from './trial-balance/list-trial-balance/list-trial-balance.component';
import { ViewTrialBalanceComponent } from './trial-balance/view-trial-balance/view-trial-balance.component';
import { EditTrialBalanceComponent } from './trial-balance/edit-trial-balance/edit-trial-balance.component';
import { CreateTrialBalanceComponent } from './trial-balance/create-trial-balance/create-trial-balance.component';
import { FinancialDataUploadsModule } from '../financial-data-uploads.module';
import { UserMgtService } from 'src/app/settings/user-mgt/services/user-mgt.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { SharedFiltersModule } from '../../sharedFilters/shared-filter.module';
import { BudgetUploadService } from './services/budget-upload.service';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBarModule } from '@angular/material/snack-bar';


@NgModule({
  declarations: [
    PeriodicUploadsComponent,
    TrialBalanceComponent,
    ListTrialBalanceComponent,
    ViewTrialBalanceComponent,
    EditTrialBalanceComponent,
    CreateTrialBalanceComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    PeriodicUploadsRoutingModule,
    SharedModule, MatTabsModule,
    FormsModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatNativeDateModule,
    MatDatepickerModule,
    SharedFiltersModule,
    MatTooltipModule,
    MatSnackBarModule

  ],
  providers: [
    UserMgtService,
    BudgetUploadService
  ],
})
export class PeriodicUploadsModule { }
