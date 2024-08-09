import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateGuard } from 'src/app/auth/services/can-activate.service';
import { AuthorityConstants } from 'src/app/authority-constants';
import { CreateHistoricalDataUploadComponent } from './create-historical-data-upload/create-historical-data-upload.component';
import { EditHistoricalDataUploadComponent } from './edit-historical-data-upload/edit-historical-data-upload.component';
import { HistoricalDataUploadComponent } from './historical-data-upload.component';
import { ListHistoricalDataUploadComponent } from './list-historical-data-upload/list-historical-data-upload.component';
import { ViewHistoricalDataUploadComponent } from './view-historical-data-upload/view-historical-data-upload.component';

const routes: Routes = [
  {
    path: '',
    component: HistoricalDataUploadComponent,
    // pathMatch: 'full',
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full',

      },
      {
        path: 'create',
        component: CreateHistoricalDataUploadComponent,
        canActivate: [CanActivateGuard],
        data: {
          breadcrumb: 'create Historical Data',
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
          ]
        },
      },
      {
        path: 'list',
        component: ListHistoricalDataUploadComponent,
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
            AuthorityConstants.ROLE_SLPMU_IT,
            AuthorityConstants.ROLE_UDHD_PSEC,
            AuthorityConstants.ROLE_UDHD_SEC,
            AuthorityConstants.ROLE_UDHD_SO,
          ]
        },
      },
      {
        path: 'view/:id',
        component: ViewHistoricalDataUploadComponent,
        canActivate: [CanActivateGuard],
        data: {
          breadcrumb: 'view',
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
          ]
        },
      },
      {
        path: 'edit/:id',
        component: EditHistoricalDataUploadComponent,
        canActivate: [CanActivateGuard],
        data: {
          breadcrumb: 'edit',
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
          ]
        },
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HistoricalDataUploadRoutingModule { }
