import { CommonModule } from "@angular/common";
import { Injector, NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { AGIRComponent } from "./ag-ir.component";
import { AGRouteModule } from "./ag-ir-routing.module";
import { MAT_DATE_LOCALE } from "@angular/material/core";
import { Router } from "@angular/router";
import { AuthExpiredInterceptor } from "../../auth/interceptors/auth-expired.interceptor";
import { AuthUnAuthorisedInterceptor } from "../../auth/interceptors/auth-unauthorised.interceptor";
import { HttpTokenInterceptor } from "../../auth/interceptors/jwt-token.interceptor";
import { MatExpansionModule } from '@angular/material/expansion';
import { AgIrStatusChartComponent } from './components/charts/ag-ir-status-chart/ag-ir-status-chart.component';
@NgModule({
  declarations: [
    AGIRComponent,
    AgIrStatusChartComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    AGRouteModule,
    MatExpansionModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true,
      deps: [
        Injector
      ]
    }, {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthUnAuthorisedInterceptor,
      multi: true,
      deps: [
        Router
      ]
    }, {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true,
      deps: [
        Injector
      ]
    },
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class AGIRModule { }
