import { Injector, NgModule } from "@angular/core";

import { CommonModule } from "@angular/common";
import { UCComponent } from "./uc.component";
import { Router, RouterModule } from "@angular/router";
import { UCRoutingModule } from "./uc-routing.module";
import { UCAppComponent } from "./uc-app/uc-app.component";
import { CreateAcdcComponent } from './ac_dc/create-acdc/create-acdc.component';
import { UpdateAcdcComponent } from './ac_dc/update-acdc/update-acdc.component';
import { ListAcdcComponent } from './ac_dc/list-acdc/list-acdc.component';
import { ViewAcdcComponent } from './ac_dc/view-acdc/view-acdc.component';
import { ACDCComponent } from "./ac_dc/acdc.component";
import { SharedModule } from "../shared/shared.module";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { ACDCService } from "./service/acdc.service";
import { AuthExpiredInterceptor } from "../auth/interceptors/auth-expired.interceptor";
import { AuthUnAuthorisedInterceptor } from "../auth/interceptors/auth-unauthorised.interceptor";
import { HttpTokenInterceptor } from "../auth/interceptors/jwt-token.interceptor";
import { ReactiveFormsModule } from "@angular/forms";
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from "@angular/material/select";
import {MatNativeDateModule, MAT_DATE_LOCALE} from '@angular/material/core';
import {MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import {MatAutocompleteModule} from '@angular/material/autocomplete';
@NgModule({
  declarations: [
    UCComponent,
    UCAppComponent,
    CreateAcdcComponent,
    UpdateAcdcComponent,
    ListAcdcComponent,
    ViewAcdcComponent,
    ACDCComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    UCRoutingModule,
    HttpClientModule,
    SharedModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatSelectModule,
    MatNativeDateModule,
    MatInputModule,
    MatSlideToggleModule,
    MatAutocompleteModule
  ],
  providers: [
    ACDCService,
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
  ],
})
export class UCModule { }
