import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanLoadGuard } from 'src/app/auth/services/can-load.service';
import { AuthorityConstants } from 'src/app/authority-constants';
import { FinancialDataUploadBaseComponent } from './financial-data-upload-base.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'deascomp'
  },
  {
    path: 'deascomp',
    component: FinancialDataUploadBaseComponent,
    data: {
      breadcrumb: 'Financial Data Upload',
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
        path: 'periodic-uploads',
        loadChildren: () => import('./periodic-uploads/periodic-uploads.module').then((m) => m.PeriodicUploadsModule),
        canLoad: [CanLoadGuard],
        data: {
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
      },
      {
        path: 'annual-financial-statement',
        loadChildren: () => import('./annual-financial-statement/annual-financial-statement.module').then((m) => m.AnnualFinancialStatementModule),
        canLoad: [CanLoadGuard],
        data: {
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
      },
      {
        path: 'historical-data-uploads',
        loadChildren: () => import('./historical-data-upload/historical-data-upload.module').then((m) => m.HistoricalDataUploadModule),
        canLoad: [CanLoadGuard],
        data: {
          breadcrumb: 'Historical Data Upload',
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
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FinancialDataUploadsRoutingModule { }
