import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CashAndBankBalanceComponent } from './cash-and-bank-balance-base-component/cash-and-bank-balance.component';

const routes: Routes = [
  {
    path: "",
    component: CashAndBankBalanceComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CashBankBalanceRoutingModule { }
