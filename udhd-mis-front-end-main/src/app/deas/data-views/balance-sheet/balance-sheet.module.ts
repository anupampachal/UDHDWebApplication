import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';

import { BalanceSheetRoutingModule } from './balance-sheet-routing.module';
import { BalanceSheetComponent } from './balance-sheet-base-component/balance-sheet.component';
import { RouterModule } from '@angular/router';
import { StateLevelBalanceSheetComponent } from './state-level-balance-sheet/state-level-balance-sheet.component';
import { DivisionLevelBalanceSheetComponent } from './division-level-balance-sheet/division-level-balance-sheet.component';
import { DistrictLevelBalanceSheetComponent } from './district-level-balance-sheet/district-level-balance-sheet.component';
import { UlbLevelBalanceSheetComponent } from './ulb-level-balance-sheet/ulb-level-balance-sheet.component';
import { StateLevelBalanceSheetTableComponent } from './state-level-balance-sheet/state-level-balance-sheet-table/state-level-balance-sheet-table.component';
import { ChildLevelBalanceSheetTableComponent } from './state-level-balance-sheet/child-level-balance-sheet-table/child-level-balance-sheet-table.component';
import { HeadLevelBalanceDetailsComponent } from './state-level-balance-sheet/head-level-balance-details/head-level-balance-details.component';
import { DivisionLevelBalanceSheetTableComponent } from './division-level-balance-sheet/division-level-balance-sheet-table/division-level-balance-sheet-table.component';
import { DivisionChildBalanceSheetTableComponent } from './division-level-balance-sheet/division-child-balance-sheet-table/division-child-balance-sheet-table.component';
import { DivisionHeadBalanceDetailsComponent } from './division-level-balance-sheet/division-head-balance-details/division-head-balance-details.component';
import { DistrictLevelBalanceSheetTableComponent } from './district-level-balance-sheet/district-level-balance-sheet-table/district-level-balance-sheet-table.component';
import { DistrictChildBalanceSheetTableComponent } from './district-level-balance-sheet/district-child-balance-sheet-table/district-child-balance-sheet-table.component';
import { DistrictHeadBalanceDetailsComponent } from './district-level-balance-sheet/district-head-balance-details/district-head-balance-details.component';
import { UlbLevelBalanceSheetTableComponent } from './ulb-level-balance-sheet/ulb-level-balance-sheet-table/ulb-level-balance-sheet-table.component';
import { UlbHeadBalanceDetailsComponent } from './ulb-level-balance-sheet/ulb-head-balance-details/ulb-head-balance-details.component';
import { BalanceSheetService } from '../../services/balance-sheet.service';
import { FormatAmountPipe } from './format-amount.pipe';
import { FormatDatePipe } from './format-date.pipe';

@NgModule({
  declarations: [
    BalanceSheetComponent,
    StateLevelBalanceSheetComponent,
    DivisionLevelBalanceSheetComponent,
    DistrictLevelBalanceSheetComponent,
    UlbLevelBalanceSheetComponent,
    StateLevelBalanceSheetTableComponent,
    ChildLevelBalanceSheetTableComponent,
    HeadLevelBalanceDetailsComponent,
    DivisionLevelBalanceSheetTableComponent,
    DivisionChildBalanceSheetTableComponent,
    DivisionHeadBalanceDetailsComponent,
    DistrictLevelBalanceSheetTableComponent,
    DistrictChildBalanceSheetTableComponent,
    DistrictHeadBalanceDetailsComponent,
    UlbLevelBalanceSheetTableComponent,
    UlbHeadBalanceDetailsComponent,
    FormatAmountPipe,
    FormatDatePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    BalanceSheetRoutingModule
  ],
  providers:[
    BalanceSheetService,
    CurrencyPipe
  ]
})
export class BalanceSheetModule { }
