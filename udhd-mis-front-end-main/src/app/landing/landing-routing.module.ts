import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanLoadGuard } from '../auth/services/can-load.service';
import { AuthorityConstants } from '../authority-constants';
import { LandingComponent } from './landing.component';

const routes: Routes = [
  {
    path:'',
    pathMatch:'full',
    redirectTo:'landing'
  },
  {
    path: 'landing',
    component: LandingComponent,
    children: [
      {
        path:'',
        redirectTo:'income-expense',
        pathMatch:'full'
      },
      {
        path: 'income-expense',
        loadChildren: () => import('./landing-data-views/income-expense/income-expense.module').then((m) => m.IncomeExpenseModule),
      },
      {
        path: 'cash-and-bank-balance',
        loadChildren: () => import('./landing-data-views/cash-bank-balance/cash-bank-balance.module').then((m) => m.CashBankBalanceModule),
      },
      {
        path: 'balance-sheet',
        loadChildren: () => import('./landing-data-views/balance-sheet/balance-sheet.module').then(m => m.BalanceSheetModule),
      }
    ]
  },



]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LandingRoutingModule { }
