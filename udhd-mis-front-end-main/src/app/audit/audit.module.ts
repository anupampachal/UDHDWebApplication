import { CommonModule } from "@angular/common";
import {  Injector, NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuditComponent } from "./audit.component";
import { AuditRouteModule } from "./audit-routing.module";
import { MAT_DATE_LOCALE } from "@angular/material/core";
import { Router } from "@angular/router";
import { AuthExpiredInterceptor } from "../auth/interceptors/auth-expired.interceptor";
import { AuthUnAuthorisedInterceptor } from "../auth/interceptors/auth-unauthorised.interceptor";
import { HttpTokenInterceptor } from "../auth/interceptors/jwt-token.interceptor";
// import { AuthInterceptor } from "../auth/interceptors/"


@NgModule({
    declarations: [
        AuditComponent,
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        AuditRouteModule,

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
      }, { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
    ]
})
export class AuditModule { }
