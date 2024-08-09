import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IncomeExpenseRoutingModule } from './income-expense-routing.module';
import { StateLevelIncomeExpenseComponent } from './state-level-income-expense/state-level-income-expense.component';
import { RouterModule } from '@angular/router';
import { SharedFiltersModule } from '../../sharedFilters/shared-filter.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatRadioModule } from '@angular/material/radio';
import { MatTabsModule } from '@angular/material/tabs';
import { IncomeExpenseTableComponent } from './state-level-income-expense/income-expense-table/income-expense-table.component';
import { IncomeExpenseGraphComponent } from './state-level-income-expense/income-expense-graph/income-expense-graph.component';
import { ChildLevelIncomeExpenseComponent } from './state-level-income-expense/child-level-income-expense/child-level-income-expense.component';
import { HeadLevelIncomeExpenseComponent } from './state-level-income-expense/head-level-income-expense/head-level-income-expense.component';
import { ChartsModule } from 'ng2-charts';
import { IncomeExpenseBaseComponentComponent } from './income-expense-base-component/income-expense-base-component.component';
import { DivisionLevelIncomeExpenseComponent } from './division-level-income-expense/division-level-income-expense.component';
import { DistrictLevelIncomeExpenseComponent } from './district-level-income-expense/district-level-income-expense.component';
import { UlbLevelIncomeExpenseComponent } from './ulb-level-income-expense/ulb-level-income-expense.component';
import { HeadLevelDivisionIncomeExpenseComponent } from './division-level-income-expense/head-level-division-income-expense/head-level-division-income-expense.component';
import { DivisionIncomeExpenseGraphComponent } from './division-level-income-expense/division-income-expense-graph/division-income-expense-graph.component';
import { DivisionIncomeExpenseTableComponent } from './division-level-income-expense/division-income-expense-table/division-income-expense-table.component';
import { ChildLevelDivisionIncomeExpenseComponent } from './division-level-income-expense/child-level-division-income-expense/child-level-division-income-expense.component';
import { ChildLevelDistrictIncomeExpenseComponent } from './district-level-income-expense/child-level-district-income-expense/child-level-district-income-expense.component';
import { DistrictIncomeExpenseGraphComponent } from './district-level-income-expense/district-income-expense-graph/district-income-expense-graph.component';
import { DistrictIncomeExpenseTableComponent } from './district-level-income-expense/district-income-expense-table/district-income-expense-table.component';
import { HeadLevelDistrictIncomeExpenseComponent } from './district-level-income-expense/head-level-district-income-expense/head-level-district-income-expense.component';
import { UlbIncomeExpenseGraphComponent } from './ulb-level-income-expense/ulb-income-expense-graph/ulb-income-expense-graph.component';
import { UlbIncomeExpenseTableComponent } from './ulb-level-income-expense/ulb-income-expense-table/ulb-income-expense-table.component';
import { HeadLevelUlbIncomeExpenseComponent } from './ulb-level-income-expense/head-level-ulb-income-expense/head-level-ulb-income-expense.component';
import { IncomeExpenseService } from '../../services/income-expense.service';
import { FormatAmountPipe } from './pipes/format-amount.pipe';

@NgModule({
  declarations: [
    StateLevelIncomeExpenseComponent,
    IncomeExpenseTableComponent,
    IncomeExpenseGraphComponent,
    ChildLevelIncomeExpenseComponent,
    HeadLevelIncomeExpenseComponent,
    IncomeExpenseBaseComponentComponent,
    DivisionLevelIncomeExpenseComponent,
    DistrictLevelIncomeExpenseComponent,
    UlbLevelIncomeExpenseComponent,
    HeadLevelDivisionIncomeExpenseComponent,
    DivisionIncomeExpenseGraphComponent,
    DivisionIncomeExpenseTableComponent,
    ChildLevelDivisionIncomeExpenseComponent,
    ChildLevelDistrictIncomeExpenseComponent,
    DistrictIncomeExpenseGraphComponent,
    DistrictIncomeExpenseTableComponent,
    HeadLevelDistrictIncomeExpenseComponent,
    UlbIncomeExpenseGraphComponent,
    UlbIncomeExpenseTableComponent,
    HeadLevelUlbIncomeExpenseComponent,
    FormatAmountPipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    IncomeExpenseRoutingModule,
    SharedModule, MatTabsModule,
    FormsModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatNativeDateModule,
    MatDatepickerModule,
    SharedFiltersModule,
    ChartsModule
  ],
  providers:[
    IncomeExpenseService,

  ]
})
export class IncomeExpenseModule { }
