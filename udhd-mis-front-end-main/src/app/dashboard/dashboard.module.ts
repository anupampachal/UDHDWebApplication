import { NgModule } from "@angular/core";
import { SharedModule } from "../shared/shared.module";

import { CommonModule } from "@angular/common";
import { DashboardComponent } from "./dashboard.component";
import { DashboardRoutingModule } from "./dashboard-route.module";
import { GraphsComponent } from './graphs/graphs.component';
import { ReportsComponent } from './reports/reports.component';
import { DeasAuditCahrtComponent } from './graphs/deas-audit-cahrt/deas-audit-cahrt.component';
import { FinanceAuditCahrtComponent } from './graphs/finance-audit-cahrt/finance-audit-cahrt.component';
import { InternalAuditCahrtComponent } from './graphs/internal-audit-cahrt/internal-audit-cahrt.component';
import { AgAuditCahrtComponent } from './graphs/ag-audit-cahrt/ag-audit-cahrt.component';
import { PacAuditCahrtComponent } from './graphs/pac-audit-cahrt/pac-audit-cahrt.component';
import { ChartsModule } from "ng2-charts";
import { DataViewsDashboardBaseComponent } from "./dashboard-data-views/data-views-base.component";
import { DataViewsDashboardBreadcrumbComponent } from "./dashboard-data-views/data-views-breadcrumb/data-views-breadcrumb.component";
import { DateFilterComponent } from "./sharedFilters/date-filter/date-filter.component";
import { DashboardUlbFilterComponent, } from "./sharedFilters/ulb-filter/ulb-filter.component";
import { FormatAmountPipe } from "./dashboard-data-views/pipe/format-amount.pipe";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatRadioModule } from "@angular/material/radio";
import { MatDatepickerModule, MatDateRangePicker } from "@angular/material/datepicker";
import { MatTabsModule } from "@angular/material/tabs";
import { IncomeExpenseService } from "./services/income-expense.service";
import { CashBankBalanceService } from "./services/cash-bank-balance.service";
import { FinancialStatementsService } from "./services/financial-statements.service";
import { HistoricalDataUploadService } from "./services/historical-data-upload.service";
import { RouteService } from "./services/route.service";
import { BalanceSheetService } from "./services/balance-sheet.service";
import { UserMgtService } from "../settings/user-mgt/services/user-mgt.service";
import { MatNativeDateModule } from "@angular/material/core";
@NgModule({
  declarations: [
    DashboardComponent,
    GraphsComponent,
    ReportsComponent,
    DeasAuditCahrtComponent,
    FinanceAuditCahrtComponent,
    InternalAuditCahrtComponent,
    AgAuditCahrtComponent,
    PacAuditCahrtComponent,
    DataViewsDashboardBreadcrumbComponent,
    DataViewsDashboardBaseComponent,
    DateFilterComponent,
    FormatAmountPipe,
    DashboardUlbFilterComponent

  ],
  imports: [
    SharedModule,
    CommonModule,
    DashboardRoutingModule,
    ChartsModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
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
  ],
})
export class DashboardModule { }
