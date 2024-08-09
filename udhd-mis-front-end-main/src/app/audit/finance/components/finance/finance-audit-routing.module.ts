import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { FinanceAuditSummaryComponent } from "./finance-audit-summary/finance-audit-summary.component";
import { CreateFinanceAuditComponent } from "./create-finance-audit/create-finance-audit.component";
import { EditFinanceAuditComponent } from "./edit-finance-audit/edit-finance-audit.component";
import { ListFinanceAuditComponent } from "./list-finance-audit/list-finance-audit.component";
import { ViewFinanceAuditComponent } from "./view-finance-audit/view-finance-audit.component";

const financeAuditRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListFinanceAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'List',
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
        path: 'create',
        component: CreateFinanceAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Create',
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
        path: 'view/:id',
        component: ViewFinanceAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'View',
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
        path: 'edit/:id',
        component: EditFinanceAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Edit',
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
        }
    }, {
        path: 'summary',
        component: FinanceAuditSummaryComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Summary',
            authorities: [
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                AuthorityConstants.ROLE_SLPMU_UC,
                AuthorityConstants.ROLE_UDHD_PSEC,
                AuthorityConstants.ROLE_UDHD_SEC,
                AuthorityConstants.ROLE_UDHD_SO,
            ]
        }
    }
]

@NgModule({
    imports: [RouterModule.forChild(financeAuditRoutes)],
    exports: [RouterModule]
})
export class FinanceAuditRouteModule { }
