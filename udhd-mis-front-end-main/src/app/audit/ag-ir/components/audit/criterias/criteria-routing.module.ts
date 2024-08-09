import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CreateAgirAuditCriteriaComponent } from "./create-agir-audit-criteria/create-agir-audit-criteria.component";
import { EditAgirAuditCriteriaComponent } from "./edit-agir-audit-criteria/edit-agir-audit-criteria.component";
import { ListAgirAuditCriteriaComponent } from "./list-agir-audit-criteria/list-agir-audit-criteria.component";
import { ViewAgirAuditCriteriaComponent } from "./view-agir-audit-criteria/view-agir-audit-criteria.component";
import { AuthorityConstants } from "src/app/authority-constants";

const agIRRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListAgirAuditCriteriaComponent,
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
        component: CreateAgirAuditCriteriaComponent,
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
        component: EditAgirAuditCriteriaComponent,
        
    }, {
        path: 'view',
        component: ViewAgirAuditCriteriaComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(agIRRoutes)],
    exports: [RouterModule]
})
export class AGCriteriaRouteModule { }