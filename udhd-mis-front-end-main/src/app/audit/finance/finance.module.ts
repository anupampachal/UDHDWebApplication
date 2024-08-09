import { CommonModule } from "@angular/common";
import { Injector, NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { FinanceComponent } from "./finance.component";
import { AGRouteModule } from "./finance-routing.module";
import { MAT_DATE_LOCALE } from "@angular/material/core";
import { Router } from "@angular/router";
import { AuthExpiredInterceptor } from "src/app/auth/interceptors/auth-expired.interceptor";
import { AuthUnAuthorisedInterceptor } from "src/app/auth/interceptors/auth-unauthorised.interceptor";
import { HttpTokenInterceptor } from "src/app/auth/interceptors/jwt-token.interceptor";
import {MatExpansionModule} from '@angular/material/expansion';
import { FinanceStatusChartComponent } from './components/charts/finance-status-chart/finance-status-chart.component';
@NgModule({
    declarations: [
        FinanceComponent,
        FinanceStatusChartComponent,
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
export class FinanceModule { }
