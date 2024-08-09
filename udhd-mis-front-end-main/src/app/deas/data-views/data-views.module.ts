import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';

import { DataViewsRoutingModule } from './data-views-routing.module';
import { DataViewsBaseComponent } from './data-views-base.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatTabsModule } from '@angular/material/tabs';
import { SharedFiltersModule } from '../sharedFilters/shared-filter.module';
import { ChartsModule } from 'ng2-charts';
import { DataViewsBreadcrumbComponent } from './data-views-breadcrumb/data-views-breadcrumb.component';
import { FormatAmountPipe } from './pipe/format-amount.pipe';

@NgModule({
  declarations: [
    DataViewsBaseComponent,
    DataViewsBreadcrumbComponent,
    FormatAmountPipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    DataViewsRoutingModule,
    SharedModule,
    MatTabsModule,
    SharedFiltersModule,
    ChartsModule
  ],
  providers: [
    CurrencyPipe
  ],
  exports: [
  ]
})
export class DataViewsModule { }
