import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanLoadGuard } from "../auth/services/can-load.service";
import { AuthorityConstants } from "../authority-constants";
import { SettingsComponent } from "./settings.component";
const serialsacRoutes: Routes = [
  {
    path: '',
    redirectTo: 'settings-home',
    pathMatch: 'full'
  }, {
    path: 'settings-home',
    component: SettingsComponent,
    data: {
      breadcrumb: 'Settings'
    },
    children: [
      {
        path: '',
        redirectTo: 'my-profile',
        pathMatch: 'full'
      }, {
        path: 'my-profile',
        loadChildren: () => import('./my-profile/my-profile.module').then(m => m.MyProfileMgtModule),
        canLoad: [CanLoadGuard],
        data: {
          breadcrumb:'My Profile',
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
        }
      }, {
        path: 'geography',
        loadChildren: () => import('./geography/components/geography.module').then(m => m.SettingsGeographyModule),
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
            AuthorityConstants.ROLE_UDHD_IT]
        }
      }, {
        path: 'user-mgt',
        loadChildren: () => import('./user-mgt/user-mgt.module').then(m => m.UserMgtModule),
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
            AuthorityConstants.ROLE_UDHD_IT]
        }
      }
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(serialsacRoutes)],
  exports: [RouterModule]
})
export class SettingsRoutingModule { }
