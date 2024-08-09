import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CanActivateGuard } from '../auth/services/can-activate.service';
import { CanLoadGuard } from '../auth/services/can-load.service';
import { AuthorityConstants } from '../authority-constants';
import { DashboardComponent } from './dashboard.component';

const serialsacRoutes: Routes = [
    {
      path: '',
      redirectTo: 'dashboard-home',
      pathMatch: 'full'
    }, {
      path: 'dashboard-home',
      component: DashboardComponent,
      canActivate:[CanActivateGuard],
      data: {
        breadcrumb: 'Dashboard',
        authorities: [ AuthorityConstants.ROLE_FLIA,
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
          redirectTo: 'income-expense'
        },
        {
          path: 'income-expense',
          loadChildren: () => import('./dashboard-data-views/income-expense/income-expense.module').then((m) => m.IncomeExpenseModule),
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
          loadChildren: () => import('./dashboard-data-views/cash-bank-balance/cash-bank-balance.module').then((m) => m.CashBankBalanceModule),

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
          loadChildren: () => import('./dashboard-data-views/balance-sheet/balance-sheet.module').then((m) => m.BalanceSheetModule),
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
    imports: [RouterModule.forChild(serialsacRoutes)],
    exports: [RouterModule]
  })
  export class DashboardRoutingModule { }
