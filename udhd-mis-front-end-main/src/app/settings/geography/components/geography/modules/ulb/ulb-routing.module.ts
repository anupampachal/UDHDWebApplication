import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { CreateUlbComponent } from "./create-ulb/create-ulb.component";
import { EditUlbComponent } from "./edit-ulb/edit-ulb.component";
import { ListUlbComponent } from "./list-ulb/list-ulb.component";
import { ULBComponent } from "./ulb.component";
import { ViewUlbComponent } from "./view-ulb/view-ulb.component";
const GeoDivisionRoutes: Routes = [
    {
        path: '',
        redirectTo: 'ulb',
        pathMatch: 'full'
    }, {
        path: 'ulb',
        component: ULBComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'ULB',
            authorities: [
                AuthorityConstants.ROLE_FLIA,
                AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                AuthorityConstants.ROLE_ULB_CMO,
                AuthorityConstants.ROLE_SLPMU_ADMIN,
                AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                AuthorityConstants.ROLE_SLPMU_AUDIT,
                AuthorityConstants.ROLE_SLPMU_UC,
                AuthorityConstants.ROLE_SLPMU_IT,
                AuthorityConstants.ROLE_UDHD_PSEC,
                AuthorityConstants.ROLE_UDHD_SEC,
                AuthorityConstants.ROLE_UDHD_SO,
                AuthorityConstants.ROLE_UDHD_IT
            ]
        },
        children: [
            {
                path: '',
                redirectTo: 'list',
                pathMatch: 'full'
            },
            {
                path: 'list',
                canActivate: [CanActivateGuard],
                component: ListUlbComponent,
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
                        AuthorityConstants.ROLE_SLPMU_IT,
                        AuthorityConstants.ROLE_UDHD_PSEC,
                        AuthorityConstants.ROLE_UDHD_SEC,
                        AuthorityConstants.ROLE_UDHD_SO,
                        AuthorityConstants.ROLE_UDHD_IT
                    ]
                }
            }, {
                path: 'create',
                canActivate: [CanActivateGuard],
                component: CreateUlbComponent,
                data: {
                    breadcrumb: 'Create',
                    authorities: [
                        AuthorityConstants.ROLE_SLPMU_IT,
                        AuthorityConstants.ROLE_UDHD_IT
                    ]
                }
            }, {
                path: 'view/:id',
                canActivate: [CanActivateGuard],
                component: ViewUlbComponent,
                data: {
                    breadcrumb: 'View',
                    authorities: [AuthorityConstants.ROLE_FLIA,
                    AuthorityConstants.ROLE_INTERNAL_AUDITOR,
                    AuthorityConstants.ROLE_ULB_ACCOUNTANT,
                    AuthorityConstants.ROLE_ULB_CMO,
                    AuthorityConstants.ROLE_SLPMU_ADMIN,
                    AuthorityConstants.ROLE_SLPMU_ACCOUNT,
                    AuthorityConstants.ROLE_SLPMU_AUDIT,
                    AuthorityConstants.ROLE_SLPMU_UC,
                    AuthorityConstants.ROLE_SLPMU_IT,
                    AuthorityConstants.ROLE_UDHD_PSEC,
                    AuthorityConstants.ROLE_UDHD_SEC,
                    AuthorityConstants.ROLE_UDHD_SO,
                    AuthorityConstants.ROLE_UDHD_IT
                    ]
                }
            }, {
                path: 'edit/:id',
                canActivate: [CanActivateGuard],
                component: EditUlbComponent,
                data: {
                    breadcrumb: 'Edit',
                    authorities: [
                        AuthorityConstants.ROLE_SLPMU_IT,
                        AuthorityConstants.ROLE_UDHD_IT
                    ]
                }
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(GeoDivisionRoutes)],
    exports: [RouterModule]
})
export class SettingsGeoDivisionRoutingModule { }
