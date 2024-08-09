import { CommonModule } from "@angular/common";
import { Injector, NgModule } from "@angular/core";
import { AuthComponent } from "./auth.component";
import { AuthRoutingModule } from "./auth.routing-module";
import { UserAccountService } from "./services/user-account.service";
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from "@angular/forms";
import { LoginService } from "./services/login.service";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Principal } from "./services/principal.service";
import { PasswordResetService } from "./services/password-reset.service";
import { CanActivateGuard } from "./services/can-activate.service";
import { CanLoadGuard } from "./services/can-load.service";
import { MatButtonModule } from "@angular/material/button";
import { MatToolbar, MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
//import { NgxCaptchaModule } from "ngx-captcha";

@NgModule({
    declarations: [
        AuthComponent,
        LoginComponent,
    ],
    imports: [
        AuthRoutingModule,
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
       // NgxCaptchaModule,
    ],
    providers: [
        UserAccountService,
        LoginService,
        Principal,
        CanActivateGuard,
        CanLoadGuard,
        PasswordResetService,

    ],
    exports:[
      LoginComponent
    ]
})
export class AuthModule { }
