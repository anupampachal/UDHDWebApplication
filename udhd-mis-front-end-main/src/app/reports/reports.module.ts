import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ListAgirComponent } from './ag-ir/list-agir/list-agir.component';
import { ViewAgirComponent } from './ag-ir/view-agir/view-agir.component';
import { ListCagPacComponent } from './cag-pac/list-cag-pac/list-cag-pac.component';
import { ViewCagPacComponent } from './cag-pac/view-cag-pac/view-cag-pac.component';
import { ListFinanceAuditReportComponent } from './financeAudit/list-finance-audit-report/list-finance-audit-report.component';
import { ViewFinanceAuditReportComponent } from './financeAudit/view-finance-audit-report/view-finance-audit-report.component';
import { ListInternalAuditReportComponent } from './internalAudit/list-internal-audit-report/list-internal-audit-report.component';
import { ViewInternalAuditReportComponent } from './internalAudit/view-internal-audit-report/view-internal-audit-report.component';
import { ListUcReportComponent } from './uc/list-uc-report/list-uc-report.component';
import { ViewUcReportComponent } from './uc/view-uc-report/view-uc-report.component';
import { AgIrComponent } from './ag-ir/ag-ir.component';
import { CagPacComponent } from './cag-pac/cag-pac.component';
import { FinanceAuditComponent } from './financeAudit/finance-audit.component';
import { InternalAuditComponent } from './internalAudit/internal-audit.component';
import { UcComponent } from './uc/uc.component';
import { ReportsComponent } from './reports.component';
import { SharedModule } from '../shared/shared.module';
import { ReportsService } from './reports.service';


@NgModule({
  declarations: [
    ListAgirComponent,
    ViewAgirComponent,
    ListCagPacComponent,
    ViewCagPacComponent,
    ListFinanceAuditReportComponent,
    ViewFinanceAuditReportComponent,
    ListInternalAuditReportComponent,
    ViewInternalAuditReportComponent,
    ListUcReportComponent,
    ViewUcReportComponent,
    AgIrComponent,
    CagPacComponent,
    FinanceAuditComponent,
    InternalAuditComponent,
    UcComponent,
    ReportsComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule
  ],
  providers:[ReportsService]
})
export class ReportsModule { }
