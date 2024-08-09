import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';

import { CashBankBalanceRoutingModule } from './cash-bank-balance-routing.module';
import { RouterModule } from '@angular/router';
import { CashAndBankBalanceComponent } from './cash-and-bank-balance-base-component/cash-and-bank-balance.component';
import { UlbLevelCashNBankBalanceComponent } from './ulb-level-cash-n-bank-balance/ulb-level-cash-n-bank-balance.component';
import { ChildLevelDistrictComponent } from './district-level-cash-n-bank-balance/child-level-district/child-level-district.component';
import { DistrictCashnBankBalanceTableComponent } from './district-level-cash-n-bank-balance/district-cashn-bank-balance-table/district-cashn-bank-balance-table.component';
import { DistrictLevelCashNBankBalanceComponent } from './district-level-cash-n-bank-balance/district-level-cash-n-bank-balance.component';
import { HeadLevelDistrictCashnBankBalanceComponent } from './district-level-cash-n-bank-balance/head-level-district-cashn-bank-balance/head-level-district-cashn-bank-balance.component';
import { ChildLevelDivisionCashBankBalanceComponent } from './division-level-cash-n-bank-balance/child-level-division-cash-bank-balance/child-level-division-cash-bank-balance.component';
import { DivisionCashBankBalanceTableComponent } from './division-level-cash-n-bank-balance/division-cash-bank-balance-table/division-cash-bank-balance-table.component';
import { DivisionLevelCashNBankBalanceComponent } from './division-level-cash-n-bank-balance/division-level-cash-n-bank-balance.component';
import { HeadLevelDivisionCashBankBalanceComponent } from './division-level-cash-n-bank-balance/head-level-division-cash-bank-balance/head-level-division-cash-bank-balance.component';
import { ChildLevelCashBankBalanceComponent } from './state-level-cash-n-bank-balance/child-level-cash-bank-balance/child-level-cash-bank-balance.component';
import { HeadLevelCashBankBalanceComponent } from './state-level-cash-n-bank-balance/head-level-cash-bank-balance/head-level-cash-bank-balance.component';
import { StateCashBankBalanceTableComponent } from './state-level-cash-n-bank-balance/state-cash-bank-balance-table/state-cash-bank-balance-table.component';
import { StateLevelCashNBankBalanceComponent } from './state-level-cash-n-bank-balance/state-level-cash-n-bank-balance.component';
import { HeadLevelUlbCashBankBalanceComponent } from './ulb-level-cash-n-bank-balance/head-level-ulb-cash-bank-balance/head-level-ulb-cash-bank-balance.component';
import { UlbCashBankBalanceTableComponent } from './ulb-level-cash-n-bank-balance/ulb-cash-bank-balance-table/ulb-cash-bank-balance-table.component';
import { ChartsModule } from 'ng2-charts';
import { CashBankBalanceService } from '../../services/cash-bank-balance.service';
import { FormatAmountPipe } from './pipes/format-amount.pipe';

@NgModule({
  declarations: [
    CashAndBankBalanceComponent,
    CashAndBankBalanceComponent,
    DistrictLevelCashNBankBalanceComponent,
    DivisionLevelCashNBankBalanceComponent,
    StateLevelCashNBankBalanceComponent,
    UlbLevelCashNBankBalanceComponent,
    ChildLevelDistrictComponent,
    DistrictCashnBankBalanceTableComponent,
    HeadLevelDistrictCashnBankBalanceComponent,
    ChildLevelDivisionCashBankBalanceComponent,
    DivisionCashBankBalanceTableComponent,
    HeadLevelDivisionCashBankBalanceComponent,
    ChildLevelCashBankBalanceComponent,
    HeadLevelCashBankBalanceComponent,
    StateCashBankBalanceTableComponent,
    HeadLevelUlbCashBankBalanceComponent,
    UlbCashBankBalanceTableComponent,
    FormatAmountPipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    ChartsModule,
    CashBankBalanceRoutingModule
  ],
  providers:[
    CashBankBalanceService,
    CurrencyPipe
  ]
})
export class CashBankBalanceModule { }

