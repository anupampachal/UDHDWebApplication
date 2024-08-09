import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IncomeExpenseBaseComponentComponent } from './income-expense-base-component/income-expense-base-component.component';

const routes: Routes = [
  {
    path: '',
    component: IncomeExpenseBaseComponentComponent,
    // pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IncomeExpenseRoutingModule { }
