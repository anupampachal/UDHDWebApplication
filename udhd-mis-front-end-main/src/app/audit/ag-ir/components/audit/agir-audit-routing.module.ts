import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "../../../../auth/services/can-activate.service";
import { AuthorityConstants } from "../../../../authority-constants";

import { CreateAgirAuditComponent } from "./create-agir-audit/create-agir-audit.component";
import { EditAgirAuditComponent } from "./edit-agir-audit/edit-agir-audit.component";
import { ListAgirAuditComponent } from "./list-agir-audit/list-agir-audit.component";
import { ViewAgirAuditComponent } from "./view-agir-audit/view-agir-audit.component";
import { ListUnassginedAgirAuditComponent } from "./list-unassigned-agir-audit/list-unassigned-agir-audit.component";
import { ListAssignedToMeAGIRAudit } from "./list-assigned-to-me-agir-audit/list-asssigned-to-me-agir-audit.component";

const agirAuditRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListAgirAuditComponent,
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
    },{
        path: 'unassigned-list',
        component: ListUnassginedAgirAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Unassigned AGIR Audit',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
               
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                
            ]
        },
    }, {
        path: 'list-assigned-to-me',
        component: ListAssignedToMeAGIRAudit,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'AGIR Audit Assigned ',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_AUDIT,   
            ]
        },
    },{
        path: 'create',
        component: CreateAgirAuditComponent,
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
        component: ViewAgirAuditComponent,
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
        component: EditAgirAuditComponent,
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
    }
]

@NgModule({
    imports: [RouterModule.forChild(agirAuditRoutes)],
    exports: [RouterModule]
})
export class AGIRAuditRouteModule { }
