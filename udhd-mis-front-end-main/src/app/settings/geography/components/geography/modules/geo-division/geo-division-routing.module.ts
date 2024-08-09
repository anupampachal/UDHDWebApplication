import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { CreateDivisionComponent } from "./create-division/create-division.component";
import { EditDivisionComponent } from "./edit-division/edit-division.component";
import { GeoDivisionComponent } from "./geo-division.component";
import { ListDivisionComponent } from "./list-division/list-division.component";
import { ViewDivisionComponent } from "./view-division/view-division.component";
const GeoDivisionRoutes: Routes = [
    {
        path: '',
        redirectTo: 'division',
        pathMatch: 'full'
    }, {
        path: 'division',
        component: GeoDivisionComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Division',
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
                component: ListDivisionComponent,
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
                component: CreateDivisionComponent,
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
                component: ViewDivisionComponent,
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
                component: EditDivisionComponent,
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
