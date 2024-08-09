import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FinancialDataUploadsRoutingModule } from './financial-data-uploads-routing.module';
import { FinancialDataUploadBaseComponent } from './financial-data-upload-base.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    FinancialDataUploadBaseComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    FinancialDataUploadsRoutingModule,

  ],
  exports:[
  ]
})
export class FinancialDataUploadsModule { }
