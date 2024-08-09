import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorityConstants } from "src/app/authority-constants";
const FinanceRoutes: Routes = [
    {
        path: '',
        redirectTo: 'finance',
        pathMatch: 'full'
    }, {
        path: 'finance',
        loadChildren: () => import('./components/finance/finance-audit.module').then((m) => m.FinanceAuditModuleInner),
        data: {
            breadcrumb: 'Finance Audit',
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
    imports: [RouterModule.forChild(FinanceRoutes)],
    exports: [RouterModule]
})
export class AGRouteModule { }
