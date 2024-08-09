import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "../auth/services/can-activate.service";
import { AuthorityConstants } from "../authority-constants";
import { ACDCComponent } from "./ac_dc/acdc.component";
import { CreateAcdcComponent } from "./ac_dc/create-acdc/create-acdc.component";
import { ListAcdcComponent } from "./ac_dc/list-acdc/list-acdc.component";
import { UpdateAcdcComponent } from "./ac_dc/update-acdc/update-acdc.component";
import { ViewAcdcComponent } from "./ac_dc/view-acdc/view-acdc.component";
import { UCAppComponent } from "./uc-app/uc-app.component";
import { UCComponent } from "./uc.component";
const ucRoutes: Routes = [
    {
        path: '',
        redirectTo: 'uc-home',
        pathMatch: 'full'
    }, {
        path: 'uc-home',
        component: UCComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'UC',
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
            redirectTo: 'uc-app',
            pathMatch: 'full'
        },
        {
            path: 'uc-app',
            component: UCAppComponent,
            canActivate: [CanActivateGuard],
            data: {
                breadcrumb: 'UC',
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

            ]
        }, {
            path: 'ac-dc',
            component: ACDCComponent,
            canActivate: [CanActivateGuard],
            data: {
                breadcrumb: 'AC-DC',
                authorities: [
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
                redirectTo: 'list',
                pathMatch: 'full'
            },
            {
                path: 'list',
                component: ListAcdcComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'List',
                    authorities: [
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

                ]
            }, {
                path: 'create',
                component: CreateAcdcComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Create',
                    authorities: [
                        AuthorityConstants.ROLE_SLPMU_ADMIN,
                        AuthorityConstants.ROLE_UDHD_SO,
                    ]
                },
                children: [

                ]
            }, {
                path: 'edit/:id',
                component: UpdateAcdcComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'Edit',
                    authorities: [
                        AuthorityConstants.ROLE_SLPMU_ADMIN,
                        AuthorityConstants.ROLE_UDHD_SO,
                    ]
                },
                children: [

                ]
            }, {
                path: 'view/:id',
                component: ViewAcdcComponent,
                canActivate: [CanActivateGuard],
                data: {
                    breadcrumb: 'View',
                    authorities: [
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

                ]
            }
            ]
        }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(ucRoutes)],
    exports: [RouterModule]
})
export class UCRoutingModule { }
