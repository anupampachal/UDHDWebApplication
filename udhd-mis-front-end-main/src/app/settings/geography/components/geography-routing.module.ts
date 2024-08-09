import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { CanLoadGuard } from "src/app/auth/services/can-load.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { SettingsGeographyComponent } from "./geography/geography.component";
const geographyRoutes: Routes = [
    {
        path: '',
        redirectTo: 'geography',
        pathMatch: 'full'
    }, {
        path: 'geography',
        component: SettingsGeographyComponent,
        canActivate: [CanActivateGuard],
        data: {
            breadcrumb: 'Geography',
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
            AuthorityConstants.ROLE_UDHD_IT]
        },
        children: [
            {
                path: '',
                redirectTo: 'division',
                pathMatch: 'full'
            },
            {
                path: 'division',
                loadChildren: () => import("./geography/modules/geo-division/geo-division.module").then(m => m.GeoDivisionModule),
                canLoad: [CanLoadGuard],
                data: {
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
            },
            {
                path: 'district',
                loadChildren: () => import("./geography/modules/geo-district/geo-district.module").then(m => m.GeoDistrictModule),
                canLoad: [CanLoadGuard],
                data: {
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
            },
            {
                path: 'ulb',
                loadChildren: () => import("./geography/modules/ulb/ulb.module").then(m => m.ULBModule),
                canLoad: [CanLoadGuard],
                data: {
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
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(geographyRoutes)],
    exports: [RouterModule]
})
export class SettingsGeographyRoutingModule { }
