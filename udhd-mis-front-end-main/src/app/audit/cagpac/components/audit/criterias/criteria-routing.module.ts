import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CreateCagAuditCriteriaComponent } from "./create-cag-audit-criteria/create-cag-audit-criteria.component";
import { EditCagAuditCriteriaComponent } from "./edit-cag-audit-criteria/edit-cag-audit-criteria.component";
import { ListCagAuditCriteriaComponent } from "./list-cag-audit-criteria/list-cag-audit-criteria.component";
import { ViewCagAuditCriteriaComponent } from "./view-cag-audit-criteria/view-cag-audit-criteria.component";
import { AuthorityConstants } from "src/app/authority-constants";

const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListCagAuditCriteriaComponent,
        data: {
            breadcrumb: 'AG-IR Audit',
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
        component: CreateCagAuditCriteriaComponent,
        data: {
            breadcrumb: 'AG-IR Audit',
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
        path: 'edit',
        component: EditCagAuditCriteriaComponent,
        
    }, {
        path: 'view',
        component: ViewCagAuditCriteriaComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCriteriaRouteModule { }