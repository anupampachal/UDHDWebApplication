import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LandingRoutingModule } from './landing-routing.module';
import { LandingComponent } from './landing.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { SharedModule } from '../shared/shared.module';
import { LandingDataViewsComponent } from './landing-data-views/landing-data-views.component';
import { AgAuditCahrtComponent } from './graph/ag-audit-cahrt/ag-audit-cahrt.component';
import { DeasAuditCahrtComponent } from './graph/deas-audit-cahrt/deas-audit-cahrt.component';
import { FinanceAuditCahrtComponent } from './graph/finance-audit-cahrt/finance-audit-cahrt.component';
import { GraphsComponent } from './graph/graphs.component';
import { InternalAuditCahrtComponent } from './graph/internal-audit-cahrt/internal-audit-cahrt.component';
import { PacAuditCahrtComponent } from './graph/pac-audit-cahrt/pac-audit-cahrt.component';
//import { FormatAmountPipe } from './landing-data-views/balance-sheet/format-amount.pipe';
import { DateFilterComponent } from './sharedFilters/date-filter/date-filter.component';
import { DashboardUlbFilterComponent } from './sharedFilters/ulb-filter/ulb-filter.component';
import { ChartsModule } from 'ng2-charts';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule, MatDateRangePicker } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatTabsModule } from '@angular/material/tabs';
import { UserMgtService } from '../settings/user-mgt/services/user-mgt.service';
import { BalanceSheetService } from './services/balance-sheet.service';
import { CashBankBalanceService } from './services/cash-bank-balance.service';
import { FinancialStatementsService } from './services/financial-statements.service';
import { HistoricalDataUploadService } from './services/historical-data-upload.service';
import { IncomeExpenseService } from './services/income-expense.service';
import { RouteService } from './services/route.service';


@NgModule({
  declarations: [
    LandingComponent,
    LandingDataViewsComponent,
    GraphsComponent,
    DeasAuditCahrtComponent,
    FinanceAuditCahrtComponent,
    InternalAuditCahrtComponent,
    AgAuditCahrtComponent,
    PacAuditCahrtComponent,
    DateFilterComponent,
    //FormatAmountPipe,
    DashboardUlbFilterComponent

  ],
  imports: [
    CommonModule,
    LandingRoutingModule,
    MatToolbarModule,
    MatIconModule,
    SharedModule,
    ChartsModule,
    ReactiveFormsModule,
    FormsModule,
    MatRadioModule,
    MatDatepickerModule,
    MatTabsModule,
    MatNativeDateModule
  ],
  providers: [
    IncomeExpenseService,
    CashBankBalanceService,
    FinancialStatementsService,
    HistoricalDataUploadService,
    RouteService,
    BalanceSheetService,
    MatDateRangePicker,
    UserMgtService
  ]
})
export class LandingModule { }
