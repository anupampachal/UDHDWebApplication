import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeasRoutingModule } from './deas-routing.module';
import { DeasComponent } from './deas.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { BalanceSheetService } from './services/balance-sheet.service';
import { FinancialStatementsService } from './services/financial-statements.service';
import { HistoricalDataUploadService } from './services/historical-data-upload.service';
import { IncomeExpenseService } from './services/income-expense.service';
import { CashBankBalanceService } from './services/cash-bank-balance.service';
import { RouteService } from './services/route.service';
@NgModule({
  declarations: [
    DeasComponent,
  ],
  imports: [
    CommonModule,
    DeasRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatDatepickerModule,

  ],
  providers: [
    IncomeExpenseService,
    BalanceSheetService,
    FinancialStatementsService,
    HistoricalDataUploadService,
    CashBankBalanceService,
    RouteService

  ],
})
export class DeasModule { }
