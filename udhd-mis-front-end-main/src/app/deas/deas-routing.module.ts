import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanLoadGuard } from '../auth/services/can-load.service';
import { AuthorityConstants } from '../authority-constants';
import { DeasComponent } from './deas.component';

const routes: Routes = [
  { path: '',
    redirectTo: 'deas',
    pathMatch:'full'
  },{
    path: 'deas',
    component: DeasComponent,
    data: {
      breadcrumb:'DEAS',
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
        redirectTo:'financial-data-uploads'
      },
      {
        path:'financial-data-uploads',
        loadChildren:()=>import('./financial-data-uploads/financial-data-uploads.module').then((m)=>m.FinancialDataUploadsModule),
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
        path:'data-views',
        loadChildren:()=>import('./data-views/data-views.module').then((m)=>m.DataViewsModule),
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

    ]
    // children:[
    //   {
    //     path: 'trial-balance',
    //     component: TrialBalanceComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'Trialbalance',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   },
    //   {
    //     path: 'income-expense',
    //     component: IncomeExpenseComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'IncomeExpense',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   },
    //   {
    //     path: 'cash-and-bank-balance',
    //     component: CashBankBalanceComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'Cash & Bank Balance',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   },
    //   {
    //     path: 'balance-sheet',
    //     component: BalanceSheetComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'Balance Sheet',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   },
    //   {
    //     path: 'financial-statements',
    //     component: FinancialStatementsComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'Financial Statements',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   },
    //   {
    //     path: 'historical-data-upload',
    //     component: HistoricalDataUploadComponent,
    //     canActivate: [CanActivateGuard],
    //     data: {
    //         breadcrumb: 'Financial Statements',
    //         authorities: [
    //             AuthorityConstants.ROLE_FLIA,
    //             AuthorityConstants.ROLE_INTERNAL_AUDITOR,
    //             AuthorityConstants.ROLE_ULB_ACCOUNTANT,
    //             AuthorityConstants.ROLE_ULB_CMO,
    //             AuthorityConstants.ROLE_SLPMU_ADMIN,
    //             AuthorityConstants.ROLE_SLPMU_ACCOUNT,
    //             AuthorityConstants.ROLE_SLPMU_AUDIT,
    //             AuthorityConstants.ROLE_SLPMU_UC,
    //             AuthorityConstants.ROLE_SLPMU_IT,
    //             AuthorityConstants.ROLE_UDHD_PSEC,
    //             AuthorityConstants.ROLE_UDHD_SEC,
    //             AuthorityConstants.ROLE_UDHD_SO,
    //         ]
    //     },
    //   }
    // ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DeasRoutingModule { }
