import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AnnualFinancialStatementRoutingModule } from './annual-financial-statement-routing.module';
import { AfsBaseComponent } from './afs-base.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedFiltersModule } from '../../sharedFilters/shared-filter.module';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTabsModule } from '@angular/material/tabs';
import { BudgetUploadComponent } from './budget-upload/budget-upload.component';
import { CreateBudgetUploadComponent } from './budget-upload/create-budget-upload/create-budget-upload.component';
import { ListBudgetUploadComponent } from './budget-upload/list-budget-upload/list-budget-upload.component';
import { FixedAssetComponent } from './fixed-asset/fixed-asset.component';
import { PropertyTaxRegisterComponent } from './property-tax-register/property-tax-register.component';
import { ListFixedAssetComponent } from './fixed-asset/list-fixed-asset/list-fixed-asset.component';
import { EditBudgetUploadComponent } from './budget-upload/edit-budget-upload/edit-budget-upload.component';
import { ViewBudgetUploadComponent } from './budget-upload/view-budget-upload/view-budget-upload.component';
import { CreateFixedAssetComponent } from './fixed-asset/create-fixed-asset/create-fixed-asset.component';
import { EditFixedAssetComponent } from './fixed-asset/edit-fixed-asset/edit-fixed-asset.component';
import { ViewFixedAssetComponent } from './fixed-asset/view-fixed-asset/view-fixed-asset.component';
import { CreatePropertyTaxRegisterComponent } from './property-tax-register/create-property-tax-register/create-property-tax-register.component';
import { EditPropertyTaxRegisterComponent } from './property-tax-register/edit-property-tax-register/edit-property-tax-register.component';
import { ListPropertyTaxRegisterComponent } from './property-tax-register/list-property-tax-register/list-property-tax-register.component';
import { ViewPropertyTaxRegisterComponent } from './property-tax-register/view-property-tax-register/view-property-tax-register.component';
import { BudgetUploadService } from './services/budget-upload.service';
import { FixedAssetService } from './services/fixed-asset.service';
import { PropertyTaxRegisterService } from './services/property-tax-register.service';
import { CreateAnnualFinancialStatementComponent } from './annualFinancialStatement/create-annual-financial-statement/create-annual-financial-statement.component';
import { EditAnnualFinancialStatementComponent } from './annualFinancialStatement/edit-annual-financial-statement/edit-annual-financial-statement.component';
import { ListAnnualFinancialStatementComponent } from './annualFinancialStatement/list-annual-financial-statement/list-annual-financial-statement.component';
import { ViewAnnualFinancialStatementComponent } from './annualFinancialStatement/view-annual-financial-statement/view-annual-financial-statement.component';
import { AnnualFinStComponent } from './annualFinancialStatement/annual-fin-st.component';

@NgModule({
  declarations: [
    AfsBaseComponent,
    CreateAnnualFinancialStatementComponent,
    EditAnnualFinancialStatementComponent,
    ListAnnualFinancialStatementComponent,
    ViewAnnualFinancialStatementComponent,
    BudgetUploadComponent,
    PropertyTaxRegisterComponent,
    FixedAssetComponent,
    ListBudgetUploadComponent,
    CreateBudgetUploadComponent,
    ViewBudgetUploadComponent,
    EditBudgetUploadComponent,
    ListPropertyTaxRegisterComponent,
    ViewPropertyTaxRegisterComponent,
    CreatePropertyTaxRegisterComponent,
    EditPropertyTaxRegisterComponent,
    ListFixedAssetComponent,
    CreateFixedAssetComponent,
    EditFixedAssetComponent,
    ViewFixedAssetComponent,
    AnnualFinStComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatSlideToggleModule,
    AnnualFinancialStatementRoutingModule,
    MatIconModule,
    SharedModule, MatTabsModule,
    MatNativeDateModule,
    SharedFiltersModule
  ],
  providers: [
    BudgetUploadService,
    FixedAssetService,
    PropertyTaxRegisterService,

  ]
})
export class AnnualFinancialStatementModule { }
