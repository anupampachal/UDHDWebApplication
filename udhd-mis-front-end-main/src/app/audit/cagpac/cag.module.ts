import { CommonModule } from "@angular/common";
import { Injector, NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";

import { CAGRouteModule } from "./cag-routing.module";
import { MAT_DATE_LOCALE, MatNativeDateModule } from "@angular/material/core";
import { Router } from "@angular/router";
import { AuthExpiredInterceptor } from "src/app/auth/interceptors/auth-expired.interceptor";
import { AuthUnAuthorisedInterceptor } from "src/app/auth/interceptors/auth-unauthorised.interceptor";
import { HttpTokenInterceptor } from "src/app/auth/interceptors/jwt-token.interceptor";
import {MatExpansionModule} from '@angular/material/expansion';
import { CagPacStatusChartComponent as CagStatusChartComponent } from './components/charts/cag-pac-status-chart/cag-pac-status-chart.component';
import { CAGComponent } from "./cag.component";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatRadioModule } from "@angular/material/radio";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { SharedModule } from "src/app/shared/shared.module";
import { MatCardModule } from "@angular/material/card";
@NgModule({
    declarations: [
      CAGComponent,
      CagStatusChartComponent,
    ],
    imports: [
      CAGRouteModule,
      SharedModule,
      CommonModule,
      ReactiveFormsModule,
      MatSelectModule,
      MatNativeDateModule,
      MatDatepickerModule,
      MatFormFieldModule,
      MatInputModule,
      MatExpansionModule,
      MatRadioModule,
      MatSlideToggleModule,
      MatSnackBarModule,
      MatDialogModule,
      MatCardModule
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
export class CAGPACModule { }
