import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { CreateDistrictComponent } from "./create-district/create-district.component";
import { EditDistrictComponent } from "./edit-district/edit-district.component";
import { GeoDistrictComponent } from "./geo-district.component";
import { ListDistrictComponent } from "./list-district/list-district.component";
import { ViewDistrictComponent } from "./view-district/view-district.component";
const GeoDivisionRoutes: Routes = [
    {
        path: '',
        redirectTo: 'district',
        pathMatch: 'full'
    }, {
        path: 'district',
        component: GeoDistrictComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'District',
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
                component: ListDistrictComponent,
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
                component: CreateDistrictComponent,
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
                component: ViewDistrictComponent,
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
                component: EditDistrictComponent,
                data: {
                    breadcrumb: 'District',
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
