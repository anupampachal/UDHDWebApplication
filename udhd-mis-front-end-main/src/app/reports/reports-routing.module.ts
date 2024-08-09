import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InternalAuditComponent } from '../audit/internal-audit/internal-audit.component';
import { AgIrComponent } from './ag-ir/ag-ir.component';
import { ListAgirComponent } from './ag-ir/list-agir/list-agir.component';
import { ViewAgirComponent } from './ag-ir/view-agir/view-agir.component';
import { CagPacComponent } from './cag-pac/cag-pac.component';
import { ListCagPacComponent } from './cag-pac/list-cag-pac/list-cag-pac.component';
import { ViewCagPacComponent } from './cag-pac/view-cag-pac/view-cag-pac.component';
import { FinanceAuditComponent } from './financeAudit/finance-audit.component';
import { ListFinanceAuditReportComponent } from './financeAudit/list-finance-audit-report/list-finance-audit-report.component';
import { ViewFinanceAuditReportComponent } from './financeAudit/view-finance-audit-report/view-finance-audit-report.component';
import { ListInternalAuditReportComponent } from './internalAudit/list-internal-audit-report/list-internal-audit-report.component';
import { ViewInternalAuditReportComponent } from './internalAudit/view-internal-audit-report/view-internal-audit-report.component';
import { ReportsComponent } from './reports.component';
import { ListUcReportComponent } from './uc/list-uc-report/list-uc-report.component';
import { UcComponent } from './uc/uc.component';
import { ViewUcReportComponent } from './uc/view-uc-report/view-uc-report.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'reports',
  },
  {
    path: 'reports',
    component: ReportsComponent,
    data: {
      breadcrumb: 'reports'
    },
    children: [
      {
        path: 'ag-ir',
        component: AgIrComponent,
        data: {
          breadcrumb: 'agir'
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
            path: 'list',
            component: ListAgirComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewAgirComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
      {
        path: 'cagPac',
        component: CagPacComponent,
        data: {
          breadcrumb: 'cag-pac'
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
            path: 'list',
            component: ListCagPacComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewCagPacComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
      {
        path: 'financeAudit',
        component: FinanceAuditComponent,
        data: {
          breadcrumb: 'finance Audit'
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
            path: 'list',
            component: ListFinanceAuditReportComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewFinanceAuditReportComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
      {
        path: 'internalAudit',
        component: InternalAuditComponent,
        data: {
          breadcrumb: 'finance Audit'
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
            path: 'list',
            component: ListInternalAuditReportComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewInternalAuditReportComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
      {
        path: 'uc',
        component: UcComponent,
        data: {
          breadcrumb: 'uc'
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
            path: 'list',
            component: ListUcReportComponent,
            data: {
              breadcrumb: 'list'
            },
          },
          {
            path: 'view/:id',
            component: ViewUcReportComponent,
            data: {
              breadcrumb: 'view'
            },
          }
        ]
      },
    ]
  }
];




@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
