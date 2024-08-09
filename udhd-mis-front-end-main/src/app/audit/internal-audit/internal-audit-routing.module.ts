import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorityConstants } from "src/app/authority-constants";
import { CreateIaComponent } from "./create-ia/create-ia.component";
import { EditIaComponent } from "./edit-ia/edit-ia.component";
import { InternalAuditComponent } from "./internal-audit.component";
import { ListIaComponent } from "./list-ia/list-ia.component";
import { ViewIaComponent } from "./view-ia/view-ia.component";
import { ListUnassignedIaComponent } from "./list-unassigned-ia/list-unassigned-ia.component";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { ListAssignedToMeIaComponent } from "./list-assigned-to-me-ia/list-assigned-to-me-ia.component";
const iaRoutes: Routes = [
    {
        path: '',
        redirectTo: 'ia',
        pathMatch: 'full'
    }, {
        path: 'ia',
        component: InternalAuditComponent,
        data: {
          breadcrumb: 'Internal Audit',
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
        children: [
            {
                path:'',
                pathMatch:'full',
                redirectTo:'ia-list'
            },
            {
                path: 'ia-list',
                component: ListIaComponent,
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
                path: 'unassigned-list',
                component: ListUnassignedIaComponent,
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
                component: ListAssignedToMeIaComponent,
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
                path: 'ia-create',
                component: CreateIaComponent,
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
                }
            }, {
                path: 'view/:id',
                component: ViewIaComponent,
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
                }
            }, {
                path: 'edit/:id',
                component: EditIaComponent,
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
    }
];
@NgModule({
    imports: [RouterModule.forChild(iaRoutes)],
    exports: [RouterModule]
})
export class InternalAuditRouteModule { }
