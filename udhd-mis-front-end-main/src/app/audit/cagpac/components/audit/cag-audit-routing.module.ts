import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "../../../../auth/services/can-activate.service";
import { AuthorityConstants } from "../../../../authority-constants";
import { CagAuditSummaryComponent } from "./cag-audit-summary/cag-audit-summary.component";
import { CreateCagAuditComponent } from "./create-cag-audit/create-cag-audit.component";
import { EditCagAuditComponent } from "./edit-cag-audit/edit-cag-audit.component";
import { ListCagAuditComponent } from "./list-cag-audit/list-cag-audit.component";
import { ViewCagAuditComponent } from "./view-cag-audit/view-cag-audit.component";
import { ListUnassginedCagAuditComponent } from "./list-unassigned-cag-audit/list-unassigned-cag-audit.component";
import { ListAssignedToMeCAGAudit } from "./list-assigned-to-me-cag-audit/list-asssigned-to-me-cag-audit.component";
import { ListCagAuditCompliancesComponent } from "./criterias/compliances/list-cag-audit-compliances/list-cag-audit-compliances.component";

const cagAuditRoutes: Routes = [
    {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
    }, {
        path: 'list',
        component: ListCagAuditComponent,
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
        component: ListUnassginedCagAuditComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Unassigned CAG Audit',
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
        component: ListAssignedToMeCAGAudit,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'CAG Audit Assigned ',
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
        component: CreateCagAuditComponent,
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
        component: ViewCagAuditComponent,
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
        component: EditCagAuditComponent,
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
        },
        children:[
            {
                path:':/id',
                redirectTo:'list',
                pathMatch:'full'
            },{
                path:'list:/id',
                component: ListCagAuditCompliancesComponent
            }
        ]   
    }, {
        path: 'summary',
        component: CagAuditSummaryComponent,
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
    imports: [RouterModule.forChild(cagAuditRoutes)],
    exports: [RouterModule]
})
export class CAGAuditRouteModule { }
