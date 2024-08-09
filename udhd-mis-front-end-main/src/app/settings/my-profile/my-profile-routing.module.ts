import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CanActivateGuard } from "src/app/auth/services/can-activate.service";
import { AuthorityConstants } from "src/app/authority-constants";
import { EditProfileComponent } from "./edit-profile/edit-profile.component";
import { MyProfileComponent } from "./my-profile.component";
import { ViewProfileComponent } from "./view-profile/view-profile.component";
const myProfileRoutes: Routes = [
  {
    path: '',
    redirectTo: 'info',
    pathMatch: 'full'
  }, {
    path: 'info',
    component: MyProfileComponent,
    canActivate: [CanActivateGuard],
    data: {
      breadcrumb: 'My Profile',
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
        component: ViewProfileComponent,
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
          AuthorityConstants.ROLE_SLPMU_IT,
          AuthorityConstants.ROLE_UDHD_PSEC,
          AuthorityConstants.ROLE_UDHD_SEC,
          AuthorityConstants.ROLE_UDHD_SO,
          AuthorityConstants.ROLE_UDHD_IT
          ]
        },
      },
      {
        path: 'edit',
        component: EditProfileComponent,
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
          AuthorityConstants.ROLE_SLPMU_IT,
          AuthorityConstants.ROLE_UDHD_PSEC,
          AuthorityConstants.ROLE_UDHD_SEC,
          AuthorityConstants.ROLE_UDHD_SO,
          AuthorityConstants.ROLE_UDHD_IT
          ]
        },
      }
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(myProfileRoutes)],
  exports: [RouterModule]
})
export class MyProfileRoutingModule { }


