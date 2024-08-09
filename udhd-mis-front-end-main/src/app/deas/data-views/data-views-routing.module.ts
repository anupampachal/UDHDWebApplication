import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateGuard } from 'src/app/auth/services/can-activate.service';
import { CanLoadGuard } from 'src/app/auth/services/can-load.service';
import { AuthorityConstants } from 'src/app/authority-constants';
import { BalanceSheetComponent } from './balance-sheet/balance-sheet-base-component/balance-sheet.component';
import { CashAndBankBalanceComponent } from './cash-bank-balance/cash-and-bank-balance-base-component/cash-and-bank-balance.component';
import { DataViewsBaseComponent } from './data-views-base.component';
import { StateLevelIncomeExpenseComponent } from './income-expense/state-level-income-expense/state-level-income-expense.component';

const routes: Routes = [
  {
    path:'',
    pathMatch:'full',
    redirectTo:'data-view'
  },
  {
    path: 'data-view',
    component: DataViewsBaseComponent,
    data: {
      breadcrumb: 'Data View',
    },

    // pathMatch: 'full',
    children: [
      {
        path: '',
        redirectTo: 'income-expense'
      },
      {
        path: 'income-expense',
        loadChildren: () => import('./income-expense/income-expense.module').then((m) => m.IncomeExpenseModule),
        canLoad: [CanLoadGuard],
        data: {
          breadcrumb: 'Income and Expense',
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
        path: 'cash-and-bank-balance',
        loadChildren: () => import('./cash-bank-balance/cash-bank-balance.module').then((m) => m.CashBankBalanceModule),

        canLoad: [CanLoadGuard],
        data: {
          breadcrumb: 'Cash and Bank Balance',
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
        path: 'balance-sheet',
        loadChildren: () => import('./balance-sheet/balance-sheet.module').then((m) => m.BalanceSheetModule),
        canLoad: [CanLoadGuard],
        data: {
          breadcrumb: 'Balance Sheet',
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

    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DataViewsRoutingModule { }
