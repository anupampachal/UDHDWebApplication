import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorityConstants } from "../authority-constants";
import { AuditComponent } from "./audit.component";
const auditRoutes: Routes = [
    {
        path: '',
        redirectTo: 'audit',
        pathMatch: 'full'
    }, {
        path: 'audit',
        component: AuditComponent,
        data: {
            breadcrumb: 'Audit',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
                AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                AuthorityConstants.ROLE_SLPMU_UC,
                AuthorityConstants.ROLE_UDHD_PSEC,
                AuthorityConstants.ROLE_UDHD_SEC,
                AuthorityConstants.ROLE_UDHD_SO,
            ]
        },
        children: [{
            path: '',
            redirectTo: 'ag-ir',
            pathMatch: 'full'
        }, {
            path: 'ag-ir',
            loadChildren: () => import('./ag-ir/ag-ir.module').then(m => m.AGIRModule),
            data: {
                authorities: [
                    AuthorityConstants.ROLE_FLIA,
                    AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                    AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                    AuthorityConstants.ROLE_ULB_CMO,
                    AuthorityConstants.ROLE_SLPMU_ADMIN,
                    AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                    AuthorityConstants.ROLE_SLPMU_AUDIT,
                    AuthorityConstants.ROLE_SLPMU_UC,
                    AuthorityConstants.ROLE_UDHD_PSEC,
                    AuthorityConstants.ROLE_UDHD_SEC,
                    AuthorityConstants.ROLE_UDHD_SO,
                ]
            },
        }, {
          path: 'cagpac',
          loadChildren: () => import('./cagpac/cag.module').then(m => m.CAGPACModule),
          data: {
              authorities: [
                  AuthorityConstants.ROLE_FLIA,
                  AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                  AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                  AuthorityConstants.ROLE_ULB_CMO,
                  AuthorityConstants.ROLE_SLPMU_ADMIN,
                  AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                  AuthorityConstants.ROLE_SLPMU_AUDIT,
                  AuthorityConstants.ROLE_SLPMU_UC,
                  AuthorityConstants.ROLE_UDHD_PSEC,
                  AuthorityConstants.ROLE_UDHD_SEC,
                  AuthorityConstants.ROLE_UDHD_SO,
              ]
          },
      },  {
        path: 'finance',
        loadChildren: () => import('./finance/finance.module').then(m => m.FinanceModule),
        data: {
            authorities: [
                AuthorityConstants.ROLE_FLIA,
                AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                AuthorityConstants.ROLE_SLPMU_UC,
                AuthorityConstants.ROLE_UDHD_PSEC,
                AuthorityConstants.ROLE_UDHD_SEC,
                AuthorityConstants.ROLE_UDHD_SO,
            ]
        },
    },
        ]
    }, {
        path: 'ia-audit',
        loadChildren: () => import('./internal-audit/internal-audit.module').then(m => m.InternalAuditModule),
        data: {
          breadcrumb: 'Audit',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
                AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                AuthorityConstants.ROLE_SLPMU_UC,
                AuthorityConstants.ROLE_UDHD_PSEC,
                AuthorityConstants.ROLE_UDHD_SEC,
                AuthorityConstants.ROLE_UDHD_SO,
            ]
        },
    }
];
@NgModule({
    imports: [RouterModule.forChild(auditRoutes)],
    exports: [RouterModule]
})
export class AuditRouteModule { }
