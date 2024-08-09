import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injector, NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { AuthExpiredInterceptor } from './auth/interceptors/auth-expired.interceptor';
import { HttpTokenInterceptor } from './auth/interceptors/jwt-token.interceptor';
import { AuthorisedComponent } from './authorised/authorised.component';
import { SharedModule } from './shared/shared.module';
import { UnauthorisedComponent } from './unauthorised/unauthorised.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthUnAuthorisedInterceptor } from './auth/interceptors/auth-unauthorised.interceptor';
import { Router } from '@angular/router';
import { ChartsModule } from 'ng2-charts';
import { MatToolbarModule } from '@angular/material/toolbar';
//import { LandingModule } from './landing/landing.module';
@NgModule({
  declarations: [
    AppComponent,
   UnauthorisedComponent,
    AuthorisedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    SharedModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    BrowserAnimationsModule,
    ChartsModule,
    MatToolbarModule,
    //LandingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthExpiredInterceptor,
    multi: true,
    deps: [
      Injector
    ]
  },{
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
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(matIconRegistry: MatIconRegistry){
    matIconRegistry.registerFontClassAlias('fontawesome', 'fa');
  }

}
